package com.softserve.skillscope.security.payment.model.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PaymentOrder(
        String status,
        String payId,
        String redirectUrl) implements Serializable {
}
