package com.softserve.skillscope.general.handler.exception.generalException;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("This e-mail is already taken");
    }
}
