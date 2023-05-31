package com.softserve.skillscope.general.handler.exception.generalException;

public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException() {
        super("You don't have sufficient rights to perform this action");
    }

    public ForbiddenRequestException(String text) {
        super(text);
    }
}
