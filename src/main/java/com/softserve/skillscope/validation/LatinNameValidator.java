package com.softserve.skillscope.validation;

import com.softserve.skillscope.validation.annotation.LatinName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LatinNameValidator implements ConstraintValidator<LatinName, String> {
    @Autowired
    private ValidationProperties validationProp;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Pattern pattern =
                Pattern.compile(validationProp.surnameRegex());
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Name must be written in Latin!");
        }
        return true;
    }

}
