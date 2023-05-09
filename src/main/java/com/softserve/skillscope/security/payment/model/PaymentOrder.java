package com.softserve.skillscope.security.payment.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PaymentOrder(
        String status,
        String payId,
        String redirectUrl) implements Serializable {
}
