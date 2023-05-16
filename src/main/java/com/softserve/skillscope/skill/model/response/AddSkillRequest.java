package com.softserve.skillscope.skill.model.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record AddSkillRequest(Set<String> skills) {
}