package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.response.TalentImageResponse;

public interface TalentMapper {
    GeneralTalent toGeneralTalent(Talent talent);
    TalentProfile toTalentProfile(Talent talent);
    TalentImageResponse toTalentImage(Talent talent);

}