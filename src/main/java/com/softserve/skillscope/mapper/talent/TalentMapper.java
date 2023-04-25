package com.softserve.skillscope.mapper.talent;

import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.TalentInfo;
import com.softserve.skillscope.talent.model.response.TalentImageResponse;

public interface TalentMapper {
    GeneralTalent toGeneralTalent(TalentInfo talent);
    TalentProfile toTalentProfile(TalentInfo talent);
    TalentImageResponse toTalentImage(TalentInfo talent);

}