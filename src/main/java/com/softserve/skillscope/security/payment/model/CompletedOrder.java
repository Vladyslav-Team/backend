package com.softserve.skillscope.security.payment.model;

import lombok.Builder;
@Builder
public record CompletedOrder(String status, String payId) {
}