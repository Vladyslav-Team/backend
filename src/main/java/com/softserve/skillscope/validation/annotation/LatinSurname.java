package com.softserve.skillscope.validation.annotation;

import com.softserve.skillscope.validation.LatinSurnameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LatinSurnameValidator.class})
public @interface LatinSurname {

    String message() default "{Surname.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
