package com.softserve.skillscope.security.payment;

public enum OrderStatus {
    CREATED,
    SAVED,
    APPROVE,
    VOIDED,
    COMPLETED,
    PAYER_ACTION_REQUIRED,
    SUCCESS,
    FAILED;
}
