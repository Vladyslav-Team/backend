package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.general.model.ImageResponse;


public interface TalentService {
    GeneralTalentResponse getAllTalentsByPage(int page);
    GeneralResponse delete(Long talentId);
    TalentProfile getTalentProfile(Long talentId);

    GeneralResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate);

    ImageResponse getTalentImage(Long talentId);
}
