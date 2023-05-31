package com.softserve.skillscope.security.payment;

import com.softserve.skillscope.security.payment.model.entity.Orders;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders findByOrderId(String orderId);

    Page<Orders> findAllBySponsorOrderByCreateDate(Pageable pageable, Sponsor sponsor);
}
