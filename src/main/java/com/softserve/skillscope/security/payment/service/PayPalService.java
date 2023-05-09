package com.softserve.skillscope.security.payment.service;

import com.softserve.skillscope.security.payment.model.CompletedOrder;
import com.softserve.skillscope.security.payment.model.PaymentOrder;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface PayPalService {
    PaymentOrder createPayment(BigDecimal amount, HttpServletRequest request);

    CompletedOrder completePayment(String token);

}
