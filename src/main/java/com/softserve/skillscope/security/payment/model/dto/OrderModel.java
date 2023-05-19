package com.softserve.skillscope.security.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.security.payment.model.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderModel(
        Long id,
        String orderId,
        String status,
        String link,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate createDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate updateDate,
        OrderStatus activation
) {
}
