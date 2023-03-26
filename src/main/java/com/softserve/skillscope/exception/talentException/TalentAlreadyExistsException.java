package com.softserve.skillscope.exception.talentException;

public class TalentAlreadyExistsException extends RuntimeException {
    public TalentAlreadyExistsException() {
        super("This e-mail is already taken");
    }
}
