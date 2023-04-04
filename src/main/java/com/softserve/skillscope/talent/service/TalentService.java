package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;

public interface TalentService {
    GeneralTalentResponse getAllTalentsByPage(int page);

    TalentProfile getTalentProfile(Long talentId);
}
