package com.softserve.skillscope.proof.model.dto;

import com.softserve.skillscope.exception.generalException.ValidationException;
import lombok.Builder;

@Builder
public record ProofCreationDto(
    String title,
    String description
){
    public ProofCreationDto(String title, String description) {
        this.title = title;
        this.description = description;

        if (!title.matches("^[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};:\'\",.<>\\/?\\\\|]*$")) {
            throw new ValidationException("Title must be written in Latin");
        }
        if (!description.matches("^[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};:\'\",.<>\\/?\\\\|]*$")) {
            throw new ValidationException("Description must be written in Latin");
        }

    }
}
