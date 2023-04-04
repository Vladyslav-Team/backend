package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;
import jakarta.transaction.Transactional;

public interface TalentService {
    GeneralTalentResponse getAllTalentsByPage(int page);

    TalentProfile getTalentProfile(Long talentId);

    TalentResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate);
}
