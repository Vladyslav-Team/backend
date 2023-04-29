package com.softserve.skillscope.validation.annotation;

import com.softserve.skillscope.validation.LatinNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({ FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LatinNameValidator.class})
public @interface LatinName {

    String message() default "{Name.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
