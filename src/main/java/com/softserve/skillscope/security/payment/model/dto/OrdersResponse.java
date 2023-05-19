package com.softserve.skillscope.security.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrdersResponse(
        Long id,
        String status,
        @JsonProperty("totalItems")
        long totalItems,
        @JsonProperty("totalPages")
        int totalPage,
        @JsonProperty("currentPage")
        int currentPage,
        List<OrderModel> orders
) {
}
