package com.softserve.skillscope.authentication;

import com.softserve.skillscope.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.exception.talentException.TalentAlreadyExistsException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.talent.model.response.JwtToken;
import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
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
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
@Getter
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final TalentRepository talentRepo;
    private final TalentProperties talentProps;
    private final PasswordEncoder passwordEncoder;

    private Map<String, String> verifiedTokens;

    @Override
    public JwtToken registration(RegistrationRequest request) {
        if (talentRepo.existsByEmail(request.email())) {
            throw new TalentAlreadyExistsException();
        }
        Talent talent = Talent.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        TalentInfo talentInfo = TalentInfo.builder()
                .location(request.location())
                .birthday(request.birthday())
                .image(checkEmptyImage(request))
                .experience("Experience is not mention")
                .build();

        talentInfo.setTalent(talent);
        talent.setTalentInfo(talentInfo);

        Talent savedTalent = talentRepo.save(talent);

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(request.email())
                .claim("id", savedTalent.getId())
                .build();
        verifiedTokens.put(request.email(), jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
        return JwtToken.builder().token(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()).build();
    }

    @Override
    public JwtToken signIn(String username) {
        Talent talent = talentRepo.findByEmail(username).orElse(null);
        if (talent == null) {
            throw new TalentNotFoundException();
        }
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(username)
                .claim("id", talent.getId())
                .build();
        verifiedTokens.put(username, jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
        return new JwtToken(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }

    @Override
    public void signOut(String details) {
        if (verifiedTokens.containsKey(details)) {
            verifiedTokens.remove(details);
        }
        else {
            throw new UnauthorizedUserException();
        }
    }

    private String checkEmptyImage(RegistrationRequest request) {
        return request.image() == null
                ? talentProps.defaultImage() : request.image();
    }
}
