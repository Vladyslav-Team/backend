package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;

public interface TalentService {
    GeneralTalentResponse getAllTalentsByPage(int page);
    TalentResponse delete(Long talentId);
    TalentProfile getTalentProfile(Long talentId);
}
