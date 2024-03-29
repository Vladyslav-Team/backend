package com.softserve.skillscope.general.mapper.talent;

import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;

public interface TalentMapper {
    GeneralTalent toGeneralTalent(Talent talent);
    TalentProfile toTalentProfile(Talent talent);
    ImageResponse toTalentImage(Talent talent);

}