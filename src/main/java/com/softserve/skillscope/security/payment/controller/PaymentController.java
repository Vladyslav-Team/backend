package com.softserve.skillscope.security.payment.controller;

import com.softserve.skillscope.security.payment.model.CompletedOrder;
import com.softserve.skillscope.security.payment.model.PaymentOrder;
import com.softserve.skillscope.security.payment.service.PayPalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/sponsors/{sponsor-id}/kudos")
@AllArgsConstructor
public class PaymentController {

    private PayPalService paypalService;
    @PostMapping(value = "/checkout")
    public PaymentOrder createPayment(
            @PathVariable("sponsor-id") Long sponsorId,
            @RequestParam("sum") BigDecimal sum) {
        return paypalService.createPayment(sum);
    }

    @PostMapping(value = "/capture")
    public CompletedOrder completePayment(
            @PathVariable("sponsor-id") Long sponsorId,
            @RequestParam("token") String token) {
        return paypalService.completePayment(token);
    }
}
