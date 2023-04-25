package com.softserve.skillscope.mapper.talent.impl;

import com.softserve.skillscope.mapper.talent.TalentMapper;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.response.TalentImageResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class TalentMapperImpl implements TalentMapper {
    @Override
    public GeneralTalent toGeneralTalent(Talent talent) {
        return GeneralTalent.builder()
                .id(talent.getId())
                .image(talent.getImage())
                .name(talent.getUser().getName())
                .surname(talent.getUser().getSurname())
                .location(talent.getLocation())
                .experience(talent.getExperience())
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
                .build();
    }

    @Override
    public TalentImageResponse toTalentImage(Talent talent) {
        return new TalentImageResponse(talent.getImage());
    }
}
