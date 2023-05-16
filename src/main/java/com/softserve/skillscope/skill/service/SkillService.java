package com.softserve.skillscope.skill.service;

import com.softserve.skillscope.skill.model.response.SkillResponse;

public interface SkillService {
    SkillResponse getAllSkillsWithFilter(String text);
}
