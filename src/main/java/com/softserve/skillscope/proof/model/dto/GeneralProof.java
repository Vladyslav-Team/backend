package com.softserve.skillscope.proof.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GeneralProof(
        @NotBlank Long id,
        @NotBlank LocalDate publicationDate,
        @NotBlank String title
) {
}
