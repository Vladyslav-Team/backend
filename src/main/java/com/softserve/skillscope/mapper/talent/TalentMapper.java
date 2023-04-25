package com.softserve.skillscope.mapper.talent;

import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.generalModel.UserImageResponse;

public interface TalentMapper {
    GeneralTalent toGeneralTalent(Talent talent);
    TalentProfile toTalentProfile(Talent talent);
    UserImageResponse toTalentImage(Talent talent);

}