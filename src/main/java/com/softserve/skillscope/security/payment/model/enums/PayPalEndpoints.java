package com.softserve.skillscope.security.payment.model.enums;

import jakarta.servlet.http.HttpServletRequest;

public enum PayPalEndpoints {
    CAPTURE("/capture"),
    CANCEL("/cancel");
    private final String path;

    PayPalEndpoints(String path) {
        this.path = path;
    }

    public static String createUrl(HttpServletRequest baseUrl, PayPalEndpoints endpoint) {
        return baseUrl.getHeader("Origin") + endpoint.path;
    }
}
