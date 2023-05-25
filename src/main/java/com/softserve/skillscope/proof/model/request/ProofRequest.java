package com.softserve.skillscope.proof.model.request;

import com.softserve.skillscope.general.handler.exception.generalException.ValidationException;
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

        if (title != null && !title.matches(regex) || description != null && !description.matches(regex)) {
            throw new ValidationException("Text must be written in Latin");
        } else if ( title != null && title.length() > 100){
            throw new ValidationException("Title length cannot be longer than 100 symbols");
        }else if ( description != null && description.length() > 2000){
            throw new ValidationException("Description length cannot be longer than 2000 symbols");
        }
    }
}
