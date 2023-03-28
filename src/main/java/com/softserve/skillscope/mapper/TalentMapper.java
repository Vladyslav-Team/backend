package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TalentMapper {
    default GeneralTalent toGeneralTalent(Talent talent) {
        return GeneralTalent.builder()
                .id(talent.getId())
                .image(talent.getTalentInfo().getImage())
                .name(talent.getName())
                .surname(talent.getSurname())
                .location(talent.getTalentInfo().getLocation()).build();
    }
}