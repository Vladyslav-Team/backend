package com.softserve.skillscope.general.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("SkillScope")
                        .version("3.2.0")
                        .description("Web-site for freelance workers")
                        .contact(new Contact()
                                .name("SkillScope")
                                .email("skillscopeteam@gmail.com")
                                .url("http://dev.skillscope.pepega.cloud/talents?page=1")));
    }
}
