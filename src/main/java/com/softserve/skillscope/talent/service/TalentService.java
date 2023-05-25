package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.skill.model.response.MostKudosedSkillsResponse;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;


public interface TalentService {
    GeneralTalentResponse getAllTalentsByPage(int page, String skills);
    TalentProfile getTalentProfile(Long talentId);

    GeneralResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate);

    ImageResponse getTalentImage(Long talentId);

    GeneralResponse addSkillsOnTalentProfile(Long talentId, AddSkillsRequest newSkills);

    GeneralResponse deleteSkillFromTalentProfile(Long talentId, Long skillId);

    MostKudosedSkillsResponse getOwnMostKudosedSkills(Long talentId);
}
