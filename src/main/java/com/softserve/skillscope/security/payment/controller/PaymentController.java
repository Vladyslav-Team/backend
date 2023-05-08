package com.softserve.skillscope.security.payment.controller;

import com.softserve.skillscope.security.payment.model.CompletedOrder;
import com.softserve.skillscope.security.payment.model.PaymentOrder;
import com.softserve.skillscope.security.payment.service.PayPalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/sponsors/{sponsor-id}/kudos")
@AllArgsConstructor
@Slf4j
public class PaymentController {

    private PayPalService paypalService;
    @PostMapping(value = "/checkout")
    public PaymentOrder createPayment(
            @PathVariable("sponsor-id") Long sponsorId,
            @RequestParam("sum") BigDecimal sum, HttpServletRequest request) {
        return paypalService.createPayment(sum, request);
    }

    @PostMapping(value = "/capture")
    public CompletedOrder completePayment(
            @PathVariable("sponsor-id") Long sponsorId,
            @RequestParam("token") String token) {
        return paypalService.completePayment(token);
    }
}
