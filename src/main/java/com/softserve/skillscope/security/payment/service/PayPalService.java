package com.softserve.skillscope.security.payment.service;

import com.softserve.skillscope.security.payment.model.dto.CompletedOrder;
import com.softserve.skillscope.security.payment.model.dto.PaymentOrder;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface PayPalService {
    PaymentOrder createPayment(Long sponsorId, BigDecimal amount, HttpServletRequest request);

    CompletedOrder completePayment(Long sponsorId, String token);

}
