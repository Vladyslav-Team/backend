package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TalentMapper {
    default TalentFlashcard toTalentFlashcard(Talent talent){
        return new TalentFlashcard(talent.getId(),
                talent.getTalentInfo().getImage(),
                talent.getName(),
                talent.getSurname(),
                talent.getTalentInfo().getLocation());
    }
}
