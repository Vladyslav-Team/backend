package com.softserve.skillscope.skill.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softserve.skillscope.skill.model.entity.Skill;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SkillResponse(Long id, List<Skill> skills) {
}
