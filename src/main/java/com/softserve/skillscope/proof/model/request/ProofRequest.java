package com.softserve.skillscope.proof.model.request;

import com.softserve.skillscope.exception.generalException.ValidationException;
import lombok.Builder;

@Builder
public record ProofRequest(
        String title,
        String description
) {
    public ProofRequest(String title, String description) {
        this.title = title;
        this.description = description;

        String regex = "^[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};:'\",.<>/?|]*$";

        if (!title.matches(regex) || !description.matches(regex)) {
            throw new ValidationException("Text must be written in Latin");
        }
    }
}
