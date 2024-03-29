package com.softserve.skillscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SkillScopeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkillScopeApplication.class, args);
    }
}
