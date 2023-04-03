package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.talent.model.response.DeletedTalent;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;

public interface TalentService {
    GeneralTalentResponse getAllTalentsByPage(int page);
    DeletedTalent delete(Long talentId);
}
