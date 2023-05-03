package com.softserve.skillscope.general.handler.exception.generalException;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String exception) {
        super(exception);
    }
}
