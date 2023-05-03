package com.softserve.skillscope.general.handler.exception.generalException;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User with such credentials is not found");
    }
}
