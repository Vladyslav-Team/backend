package com.softserve.skillscope.security.payment.service.impl;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.security.config.PaypalConfiguration;
import com.softserve.skillscope.security.payment.OrderStatus;
import com.softserve.skillscope.security.payment.model.CompletedOrder;
import com.softserve.skillscope.security.payment.model.PaymentOrder;
import com.softserve.skillscope.security.payment.service.PayPalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.softserve.skillscope.security.payment.PayPalEndpoints.*;

@Slf4j
@Service
@AllArgsConstructor
public class PayPalServiceImpl implements PayPalService {

    private PayPalHttpClient payPalHttpClient;
    private PaypalConfiguration paypalConfig;

    @Override
    public PaymentOrder createPayment(BigDecimal amount, HttpServletRequest request) {
        OrdersCreateRequest createRequest = new OrdersCreateRequest();
        createRequest.requestBody(createOrderRequest(amount, request));
        try {
            HttpResponse<Order> response = payPalHttpClient.execute(createRequest);
            Order order = response.result();

            String redirectUrl = order.links().stream()
                    .filter(link -> OrderStatus.APPROVE.name().toLowerCase().equals(link.rel()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("No approved link found in response"))
                    .href();

            return new PaymentOrder(OrderStatus.SUCCESS.toString(), order.id(), redirectUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
            return PaymentOrder.builder()
                    .status(e.getLocalizedMessage())
                    .build();
        }
    }

    @Override
    public CompletedOrder completePayment(String token) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                return new CompletedOrder(OrderStatus.SUCCESS.toString(), token);
            }
        } catch (IOException e) {
            log.error("Failed to capture payment with PayPal", e);
        }
        return new CompletedOrder(OrderStatus.FAILED.toString(), token);
    }

    private OrderRequest createOrderRequest(BigDecimal amount, HttpServletRequest url) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown()
                .currencyCode(paypalConfig.currencyCode())
                .value(amount.toString());

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountBreakdown);

        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));

        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl(createUrl(url, CAPTURE))
                .cancelUrl(createUrl(url, CANCEL));

        orderRequest.applicationContext(applicationContext);

        return orderRequest;
    }
}
