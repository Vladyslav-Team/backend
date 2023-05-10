package com.softserve.skillscope.security.payment.controller;

import com.softserve.skillscope.security.payment.model.dto.CompletedOrder;
import com.softserve.skillscope.security.payment.model.dto.PaymentOrder;
import com.softserve.skillscope.security.payment.service.PayPalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/sponsors/{sponsor-id}/kudos")
@AllArgsConstructor
@Tag(name = "Payment", description = "API for Payment")
@Slf4j
public class PaymentController {

    private PayPalService paypalService;

    @PostMapping(value = "/checkout")
    @Operation(summary = "Checkout payment")
    public PaymentOrder createPayment(
            @PathVariable("sponsor-id") Long sponsorId,
            @RequestParam("sum") BigDecimal sum, HttpServletRequest request) {
        return paypalService.createPayment(sponsorId, sum, request);
    }

    @PostMapping(value = "/capture")
    @Operation(summary = "Complete payment")
    public CompletedOrder completePayment(
            @PathVariable("sponsor-id") Long sponsorId,
            @RequestParam("token") String token) {
        return paypalService.completePayment(sponsorId,token);
    }
}
