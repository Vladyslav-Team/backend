package com.softserve.skillscope.security.payment;

import com.softserve.skillscope.security.payment.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders findByOrderId(String orderId);
}
