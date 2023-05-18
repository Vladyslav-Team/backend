package com.softserve.skillscope.user.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user")
public record UserProperties(int userPageSize, String defaultImage, int maxKudosAmount) {
}
