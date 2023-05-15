package com.softserve.skillscope.skill.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SkillResponse(Long id, List<Skill> skills) {
}
