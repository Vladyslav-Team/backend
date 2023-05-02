package com.softserve.skillscope.config.impl;

import com.softserve.skillscope.config.AuthenticationService;
import com.softserve.skillscope.config.JwtToken;
import com.softserve.skillscope.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.exception.generalException.UserAlreadyExistsException;
import com.softserve.skillscope.exception.generalException.UserNotFoundException;
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
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
@Getter
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepo;
    private final UserProperties userProps;
    private final PasswordEncoder passwordEncoder;

    private Map<String, String> verifiedTokens;

    @Override
    public JwtToken registration(RegistrationRequest request) {
        if (userRepo.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException();
        }
        User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(request.roles())
                .build();
        if (request.roles().contains(Role.TALENT)) {
            Talent talentInfo = Talent.builder()
                    .location(request.location())
                    .birthday(request.birthday())
                    .image(checkEmptyUserImage(request))
                    .experience("Experience is not mention")
                    .build();
            talentInfo.setUser(user);
            user.setTalent(talentInfo);
        }
        if (request.roles().contains(Role.SPONSOR)) {
            Sponsor sponsorInfo = Sponsor.builder()
                    .location(request.location())
                    .birthday(request.birthday())
                    .image(checkEmptyUserImage(request))
                    .build();
            sponsorInfo.setUser(user);
            user.setSponsor(sponsorInfo);
        }


        User saveUser = userRepo.save(user);
        return signIn(saveUser.getEmail());
    }

    @Override
    public JwtToken signIn(String username) {
        User user = userRepo.findByEmail(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(user.getEmail())
                .claim("id", user.getId())
                .build();
        verifiedTokens.put(username, jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
        return new JwtToken(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }

    @Override
    public void signOut(String details) {
        if (verifiedTokens.containsKey(details)) {
            verifiedTokens.remove(details);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    private String checkEmptyUserImage(RegistrationRequest request) {
        return request.image() == null
                ? userProps.defaultImage() : request.image();
    }


}
