package com.softserve.skillscope.general.handler.exception.skillException;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException() {
        super("Skill with such credentials is not found");
    }

    public SkillNotFoundException(String exception) {
        super(exception);
    }
}