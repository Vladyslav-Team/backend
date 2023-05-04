package com.softserve.skillscope.security.auth.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.general.handler.exception.generalException.UserAlreadyExistsException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.security.auth.service.AuthenticationService;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import com.softserve.skillscope.user.model.UserProperties;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Getter
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepo;
    private final UserProperties userProps;
    private final PasswordEncoder passwordEncoder;
    private UtilService utilService;

    private Map<String, String> verifiedTokens;

//    @Override
//    public JwtToken registration(RegistrationRequest request) {
//        if (userRepo.existsByEmail(request.email())) {
//            throw new UserAlreadyExistsException();
//        }
//        User user = User.builder()
//                .name(request.name())
//                .surname(request.surname())
//                .email(request.email())
//                .password(passwordEncoder.encode(request.password()))
//                .roles(request.roles())
//                .build();
//        if (request.roles().contains(Role.TALENT)) {
//            Talent talentInfo = Talent.builder()
//                    .location(request.location())
//                    .birthday(request.birthday())
//                    .image(utilService.checkEmptyUserImage(request))
//                    .experience("Experience is not mentioned")
//                    .build();
//            talentInfo.setUser(user);
//            user.setTalent(talentInfo);
//        }
//        if (request.roles().contains(Role.SPONSOR)) {
//            Sponsor sponsorInfo = Sponsor.builder()
//                    .location(request.location())
//                    .birthday(request.birthday())
//                    .image(utilService.checkEmptyUserImage(request))
//                    .build();
//            sponsorInfo.setUser(user);
//            user.setSponsor(sponsorInfo);
//        }
//
//
//        User saveUser = userRepo.save(user);
//        return generateJwtToken(request.email(), saveUser.getId());
//    }
    @Override
    public JwtToken registration(RegistrationRequest request) {
        if (userRepo.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException();
        }
        Set<Role> roles = EnumSet.copyOf(request.roles());
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }
        Role role = roles.iterator().next();
        if (role == null) {
            throw new IllegalArgumentException("Invalid user role");
        }
        User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles.stream().map(Role::getRoleName).collect(Collectors.toSet()))
                .build();
        if (role == Role.TALENT) {
            Talent talentInfo = Talent.builder()
                    .location(request.location())
                    .birthday(request.birthday())
                    .image(utilService.checkEmptyUserImage(request))
                    .experience("Experience is not mentioned")
                    .build();
            talentInfo.setUser(user);
            user.setTalent(talentInfo);
        } else if (role == Role.SPONSOR) {
            Sponsor sponsorInfo = Sponsor.builder()
                    .location(request.location())
                    .birthday(request.birthday())
                    .image(utilService.checkEmptyUserImage(request))
                    .build();
            sponsorInfo.setUser(user);
            user.setSponsor(sponsorInfo);
        } else {
            throw new IllegalArgumentException("Invalid user role");
        }
//        user.setRoles(roles);
        User saveUser = userRepo.save(user);
        return generateJwtToken(request.email(), saveUser.getId(), role.getAuthority());
    }

    @Override
    public JwtToken signIn(String username) {
        User user = userRepo.findByEmail(username).orElseThrow(UserNotFoundException::new);
        Set<Role> roles = user.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet());
        return generateJwtToken(username, user.getId(), roles.iterator().next().getAuthority());
    }

    @Override
    public void signOut(String details) {
        if (verifiedTokens.containsKey(details)) {
            verifiedTokens.remove(details);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    private JwtToken generateJwtToken(String subject, Long id, String role) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(subject)
                .claim("id", id)
                .claim("role", role)
                .build();
        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        verifiedTokens.put(subject, tokenValue);
        return JwtToken.builder().token(tokenValue).build();
    }
}