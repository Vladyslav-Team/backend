package com.softserve.skillscope.proof.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.response.ProofStatus;

import java.time.LocalDate;

public record ProofEditRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate publicationDate,
        String title,
        String description,
        ProofStatus status
) {
}
