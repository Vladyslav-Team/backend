package com.softserve.skillscope.exception.talentException;

public class TalentNotFoundException extends RuntimeException {
    public TalentNotFoundException() {
        super("User with such credentials is not found");
    }

    public TalentNotFoundException(String message) {
        super(message);
    }
}
