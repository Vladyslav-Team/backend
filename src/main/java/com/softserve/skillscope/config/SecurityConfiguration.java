package com.softserve.skillscope.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Routing security filter
        http.authorizeHttpRequests(req -> req
                .requestMatchers(antMatcher("/h2/")).permitAll() //works only for testing
                .requestMatchers(antMatcher("/error")).permitAll()
                .requestMatchers(HttpMethod.GET, "/talents").permitAll()
                .requestMatchers(HttpMethod.GET, "/talents/{talent-id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/talents").permitAll()
                .requestMatchers(HttpMethod.POST, "/talents/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/talents/logout").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/talents/{talent-id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/talents/{talent-id}").authenticated()
                .anyRequest().denyAll());

        //HTTP session state management
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        //CORS and CSRF disabled
        http.httpBasic(Customizer.withDefaults());
        http.csrf().disable();
        http.cors().disable();
        http.headers().frameOptions().disable();

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling(c -> c
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
