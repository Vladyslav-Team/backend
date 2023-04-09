package com.softserve.skillscope.talent.model.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "talent")
public record TalentProperties(int talentPageSize) {
}
