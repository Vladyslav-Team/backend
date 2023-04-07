package com.softserve.skillscope.mapper.impl;

import com.softserve.skillscope.mapper.TalentMapper;
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
                .image(talent.getTalentInfo().getImage())
                .name(talent.getName())
                .surname(talent.getSurname())
                .location(talent.getTalentInfo().getLocation())
                .experience(talent.getTalentInfo().getExperience())
                .build();
    }

    @Override
    public TalentProfile toTalentProfile(Talent talent) {
        return TalentProfile.builder()
                .id(talent.getId())
                .image(talent.getTalentInfo().getImage())
                .name(talent.getName())
                .surname(talent.getSurname())
                .experience(talent.getTalentInfo().getExperience())
                .location(talent.getTalentInfo().getLocation())
                .about(talent.getTalentInfo().getAbout())
                .education(talent.getTalentInfo().getEducation())
                .age(Period.between(talent.getTalentInfo().getBirthday(), LocalDate.now()).getYears())
                .email(talent.getEmail())
                .phone(talent.getTalentInfo().getPhone())
                .build();
    }

    @Override
    public TalentImageResponse toTalentImage(Talent talent) {
        return new TalentImageResponse(talent.getTalentInfo().getImage());
    }
}
