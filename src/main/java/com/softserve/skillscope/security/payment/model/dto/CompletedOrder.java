package com.softserve.skillscope.security.payment.model.dto;

import lombok.Builder;
@Builder
public record CompletedOrder(String status, String payId) {
}