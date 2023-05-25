package com.softserve.skillscope.skill.model.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MostKudosedSkillsResponse(List<Long> mostKudosedSkillsId){}
