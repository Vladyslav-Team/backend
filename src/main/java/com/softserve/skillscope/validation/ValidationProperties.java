package com.softserve.skillscope.validation;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "validation")
public record ValidationProperties(String nameRegex, String surnameRegex) {
}
