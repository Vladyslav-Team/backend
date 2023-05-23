package com.softserve.skillscope.security.payment.service;

import com.softserve.skillscope.security.payment.model.dto.OrdersResponse;

public interface OrdersService {
    OrdersResponse getAllOrders(Long sponsorId, int page, int size);
}
