package com.softserve.skillscope.proof.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GeneralProof(
        @NotBlank Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        @NotBlank LocalDate publicationDate,
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank ProofStatus status
) {
}
