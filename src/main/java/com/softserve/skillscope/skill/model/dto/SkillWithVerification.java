package com.softserve.skillscope.skill.model.dto;

import com.softserve.skillscope.skill.model.entity.Skill;

public record SkillWithVerification(
        Skill skill,
        boolean verified
) {
}
