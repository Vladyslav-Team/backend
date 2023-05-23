package com.softserve.skillscope.skill.service;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.kudos.model.request.KudosAmountRequest;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.skill.model.response.SkillResponse;

public interface SkillService {
    SkillResponse getAllSkillsWithFilter(String text);

    KudosResponse showAmountKudosOfSkill(Long proofId, Long skillId);

    GeneralResponse addKudosToSkillBySponsor(Long proofId, Long skillId, KudosAmountRequest amount);
}
