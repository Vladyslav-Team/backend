package com.softserve.skillscope.mapper.impl;

import com.softserve.skillscope.mapper.TalentMapper;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.stereotype.Component;

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
}
