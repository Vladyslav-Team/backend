package com.softserve.skillscope.security.payment.model.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record OrdersResponse(
        List<OrderModel> orders
) {
}
