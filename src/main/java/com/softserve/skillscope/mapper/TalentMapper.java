package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface TalentMapper {
    GeneralTalent toGeneralTalent(Talent talent);
}