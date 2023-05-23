package com.softserve.skillscope.general.mapper.talent.impl;

import com.softserve.skillscope.general.mapper.talent.TalentMapper;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@AllArgsConstructor
public class TalentMapperImpl implements TalentMapper {

    private UtilService utilService;

    @Override
    public GeneralTalent toGeneralTalent(Talent talent) {
        return GeneralTalent.builder()
                .id(talent.getId())
                .image(talent.getImage())
                .name(talent.getUser().getName())
                .surname(talent.getUser().getSurname())
                .location(talent.getLocation())
                .experience(talent.getExperience())
                //TODO @SEM make it dynamic so text sorts as the filter words
                .skills(talent.getSkills())
                .build();
    }

    @Override
    public TalentProfile toTalentProfile(Talent talent) {
        return TalentProfile.builder()
                .id(talent.getId())
                .image(talent.getImage())
                .name(talent.getUser().getName())
                .surname(talent.getUser().getSurname())
                .experience(talent.getExperience())
                .location(talent.getLocation())
                .about(talent.getAbout())
                .education(talent.getEducation())
                .age(talent.getBirthday() != null ? Period.between(talent.getBirthday(), LocalDate.now()).getYears() : 0)
                .email(talent.getUser().getEmail())
                .phone(talent.getPhone())
                .skills(talent.getSkills())
                .balance(calculateTotalKudosAmount4CurrentUser(talent))
                .build();
    }

    @Override
    public ImageResponse toTalentImage(Talent talent) {
        return new ImageResponse(talent.getImage());
    }

    private Integer calculateTotalKudosAmount4CurrentUser(Talent talent) {
        return utilService.isNotCurrentUser(talent.getUser()) ?
                null :
                talent.getProofs().stream()
                        .filter(proof -> proof.getStatus() == ProofStatus.PUBLISHED)
                        .flatMapToInt(proof -> proof.getKudos().stream().mapToInt(Kudos::getAmount)).sum();
    }
}
