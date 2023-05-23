package com.softserve.skillscope.skill.model.response;

import com.softserve.skillscope.skill.model.entity.Skill;
import lombok.Builder;

import java.util.List;

@Builder
public record MostKudosedSkillsResponse(List<Skill> mostKudosedSkills){}
