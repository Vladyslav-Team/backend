package com.softserve.skillscope.general.handler.exception.generalException;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("You are not authorised");
    }
}
