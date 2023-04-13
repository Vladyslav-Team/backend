package com.softserve.skillscope.proof.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ProofCreationDto(

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:\'\",.<>\\/?\\\\|]*$",
             message = "Title must be written in Latin")
    String title,
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:\'\",.<>\\/?\\\\|]*$",
            message = "Description must be written in Latin")
    String description
){}
