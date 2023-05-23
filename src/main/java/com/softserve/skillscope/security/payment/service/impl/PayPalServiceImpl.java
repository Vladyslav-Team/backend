package com.softserve.skillscope.security.payment.service.impl;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.security.config.PaypalConfiguration;
import com.softserve.skillscope.security.payment.OrdersRepository;
import com.softserve.skillscope.security.payment.model.dto.CompletedOrder;
import com.softserve.skillscope.security.payment.model.dto.PaymentOrder;
import com.softserve.skillscope.security.payment.model.entity.Orders;
import com.softserve.skillscope.security.payment.model.enums.OrderStatus;
import com.softserve.skillscope.security.payment.service.PayPalService;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.softserve.skillscope.security.payment.model.enums.PayPalEndpoints.*;

@Slf4j
@Service
@AllArgsConstructor
public class PayPalServiceImpl implements PayPalService {

    private PayPalHttpClient payPalHttpClient;
    private PaypalConfiguration paypalConfig;
    private UtilService utilService;
    private OrdersRepository ordersRepo;

    @Override
    public PaymentOrder createPayment(Long sponsorId, BigDecimal amount, HttpServletRequest request) {
        Sponsor sponsor = utilService.findUserById(sponsorId).getSponsor();
        if (utilService.isNotCurrentUser(sponsor.getUser())) {
            throw new ForbiddenRequestException();
        }
        OrdersCreateRequest createRequest = new OrdersCreateRequest();
        createRequest.requestBody(createOrderRequest(amount, request));
        try {
            HttpResponse<Order> response = payPalHttpClient.execute(createRequest);
            Order order = response.result();

            String redirectUrl = order.links().stream()
                    .filter(link -> OrderStatus.APPROVE.name().equalsIgnoreCase(link.rel()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("No approved link found in response"))
                    .href();

            Orders saveOrder = Orders.builder()
                    .sponsor(sponsor)
                    .orderId(order.id()) //token
                    .status(order.status())
                    .createDate(LocalDate.now())
                    .activation(OrderStatus.PAYER_ACTION_REQUIRED)
                    .link(redirectUrl)
                    .build();
            ordersRepo.save(saveOrder);
            return new PaymentOrder(OrderStatus.SUCCESS.toString(), order.id(), redirectUrl);
        } catch (IOException e) {
            return PaymentOrder.builder()
                    .status(e.getLocalizedMessage())
                    .build();
        }
    }

    @Override
    public CompletedOrder completePayment(Long sponsorId, String token) {
        Sponsor sponsor = utilService.findUserById(sponsorId).getSponsor();
        if (utilService.isNotCurrentUser(sponsor.getUser())) {
            throw new ForbiddenRequestException();
        }
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                Orders oldOrder = ordersRepo.findByOrderId(token);
                oldOrder.setActivation(OrderStatus.READY_TO_USE);
                oldOrder.setStatus(OrderStatus.COMPLETED.name());
                oldOrder.setUpdateDate(LocalDate.now());
                oldOrder.setLink(OrderStatus.SUCCESS.toString());
                ordersRepo.save(oldOrder);
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
