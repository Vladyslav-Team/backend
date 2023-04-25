package com.softserve.skillscope.authentication;

import com.softserve.skillscope.config.JwtToken;
import com.softserve.skillscope.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.exception.talentException.TalentAlreadyExistsException;
import com.softserve.skillscope.sponsor.SponsorRepository;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.sponsor.model.entity.SponsorInfo;
import com.softserve.skillscope.sponsor.model.entity.SponsorProperties;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
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
public class SponsorAuthServiceImpl implements SponsorAuthService{
    private final JwtEncoder jwtEncoder;
    private final SponsorRepository sponsorRepo;
    private final SponsorProperties sponsorProps;
    private final PasswordEncoder passwordEncoder;

    private Map<String, String> verifiedTokens;

    @Override
    public JwtToken registrationSponsor(RegistrationRequest registrationRequest) {
        if (sponsorRepo.existsByEmail(registrationRequest.email())) {
            throw new TalentAlreadyExistsException();
        }
        Sponsor sponsor = Sponsor.builder()
                .name(registrationRequest.name())
                .surname(registrationRequest.surname())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .build();

        SponsorInfo sponsorInfo = SponsorInfo.builder()
                .location(registrationRequest.location())
                .birthday(registrationRequest.birthday())
                .image(checkEmptyImage(registrationRequest))
                .build();

        sponsorInfo.setSponsor(sponsor);
        sponsor.setSponsorInfo(sponsorInfo);

        Sponsor savedSponsor = sponsorRepo.save(sponsor);

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(registrationRequest.email())
                .claim("id", savedSponsor.getId())
                .build();
        verifiedTokens.put(registrationRequest.email(), jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
        return JwtToken.builder().token(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()).build();
    }

    @Override
    public JwtToken signInSponsor(String username) {
        Sponsor sponsor = sponsorRepo.findByEmail(username).orElse(null);
        if (sponsor == null) {
            throw new UserNotFoundException();
        }
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(username)
                .claim("id", sponsor.getId())
                .build();
        verifiedTokens.put(username, jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
        return new JwtToken(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }

    @Override
    public void signOutSponsor(String details) {
        if (verifiedTokens.containsKey(details)) {
            verifiedTokens.remove(details);
        }
        else {
            throw new UnauthorizedUserException();
        }
    }

    private String checkEmptyImage(RegistrationRequest request) {
        return request.image() == null
                ? sponsorProps.defaultImage() : request.image();
    }
}
