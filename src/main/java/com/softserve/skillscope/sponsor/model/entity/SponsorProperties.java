package com.softserve.skillscope.sponsor.model.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sponsor")
public record SponsorProperties(int defaultBalance) {
}
