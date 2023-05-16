package com.softserve.skillscope.skill.model.request;

import lombok.Builder;

import java.util.Set;

@Builder
public record AddSkillsRequest(Set<String> skills) {
}
