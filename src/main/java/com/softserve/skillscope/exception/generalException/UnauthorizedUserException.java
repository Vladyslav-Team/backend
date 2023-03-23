package com.softserve.skillscope.exception.generalException;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("You are not authorised");
    }
}
