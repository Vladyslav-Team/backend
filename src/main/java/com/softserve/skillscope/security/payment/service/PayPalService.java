package com.softserve.skillscope.security.payment.service;

import com.softserve.skillscope.security.payment.model.CompletedOrder;
import com.softserve.skillscope.security.payment.model.PaymentOrder;

import java.math.BigDecimal;

public interface PayPalService {
    PaymentOrder createPayment(BigDecimal amount);

    CompletedOrder completePayment(String token);
}
