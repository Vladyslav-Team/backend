package com.softserve.skillscope.security.auth.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.security.auth.service.AuthenticationService;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Getter
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;
    private UtilService utilService;
    private JwtEncoder jwtEncoder;
    private Map<String, String> verifiedTokens;

    @Override
    public JwtToken registrationTalent(RegistrationRequest request) {
        if (!request.roles().contains(Role.TALENT)) {
            throw new BadRequestException("Invalid user role");
        }
        User user = utilService.createUser(request);
        Talent talentInfo = Talent.builder()
                .location(request.location())
                .birthday(request.birthday())
                .image(utilService.checkEmptyUserImage(request))
                .experience("Experience is not mentioned")
                .build();
        talentInfo.setUser(user);
        user.setTalent(talentInfo);
        User saveUser = userRepo.save(user);
        Collection<? extends GrantedAuthority> auth =
                saveUser.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
        return generateJwtToken(request.email(), saveUser.getId(), auth);
    }

    @Override
    public JwtToken registerSponsor(RegistrationRequest request) {
        if (!request.roles().contains(Role.SPONSOR)) {
            throw new BadRequestException("Invalid user role");
        }
        User user = utilService.createUser(request);
        Sponsor sponsor = Sponsor.builder()
                .location(request.location())
                .birthday(request.birthday())
                .image(utilService.checkEmptyUserImage(request))
                .build();
        sponsor.setUser(user);
        user.setSponsor(sponsor);
        User saveUser = userRepo.save(user);
        Collection<? extends GrantedAuthority> auth =
                saveUser.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
        return generateJwtToken(request.email(), saveUser.getId(), auth);
    }

    @Override
    public JwtToken signInTalent(String username) {
        User user = utilService.findUserByEmail(username);
        if (!user.getRoles().contains(Role.TALENT.getAuthority())) throw new ForbiddenRequestException();

        return generateJwtToken(username, user.getId(), user.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
    }

    @Override
    public void signOutTalent(String details) {
        User user = utilService.findUserByEmail(details);
        if (!user.getRoles().contains(Role.TALENT.getAuthority())) throw new ForbiddenRequestException();
        deleteToken(details);
    }

    @Override
    public JwtToken signInSponsor(String username) {
        User user = utilService.findUserByEmail(username);
        if (!user.getRoles().contains(Role.SPONSOR.getAuthority())) throw new ForbiddenRequestException();

        return generateJwtToken(username, user.getId(), user.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
    }

    @Override
    public void signOutSponsor(String details) {
        User user = utilService.findUserByEmail(details);
        if (!user.getRoles().contains(Role.SPONSOR.getAuthority())) throw new ForbiddenRequestException();
        deleteToken(details);
    }

    private void deleteToken(String details) {
        if (verifiedTokens.containsKey(details)) {
            verifiedTokens.remove(details);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public JwtToken generateJwtToken(String subject, Long id, Collection<? extends GrantedAuthority> role) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(subject)
                .claim("scope", role.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
                .claim("id", id)
                .build();
        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        verifiedTokens.put(subject, tokenValue);
        return JwtToken.builder().token(tokenValue).build();
    }
}
