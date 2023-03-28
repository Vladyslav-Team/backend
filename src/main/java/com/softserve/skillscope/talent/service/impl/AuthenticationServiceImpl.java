package com.softserve.skillscope.talent.service.impl;

import com.softserve.skillscope.exception.talentException.TalentAlreadyExistsException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.RegistrationRequest;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.service.interfaces.AuthenticationService;
import com.softserve.skillscope.talentInfo.TalentInfoRepository;
import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final TalentRepository repository;

    private final TalentInfoRepository infoRepository;
    @Override
    public String registration(RegistrationRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new TalentAlreadyExistsException();
        }
        Talent talent = Talent.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(request.password())
                .build();
        TalentInfo info = TalentInfo.builder()
                .location(request.location())
                .age(request.dateOfBirth())
                .build();
        talent.setTalentInfo(info);
        //todo fix this
        Talent savedTalent = repository.save(talent);

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SkillScope")
                .issuedAt(now)
                .expiresAt(now.plus(45, ChronoUnit.MINUTES))
                .subject(request.email())
                .claim("id", savedTalent.getId())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String login(String username) {
        Talent talent = repository.findByEmail(username).orElse(null);
        if  (talent == null) {
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
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
