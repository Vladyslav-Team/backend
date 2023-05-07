package com.softserve.skillscope.security.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "paypal")
public record PaypalConfiguration(
        String clientId,
        String clientSecret,
        String returnUrl,
        String cancelUrl) {

    @Bean
    public PayPalHttpClient paypalHttpClient() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        return new PayPalHttpClient(environment);
    }
}
