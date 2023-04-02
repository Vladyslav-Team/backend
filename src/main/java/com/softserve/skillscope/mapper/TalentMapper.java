package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.entity.Talent;

public interface TalentMapper {
    GeneralTalent toGeneralTalent(Talent talent);
}