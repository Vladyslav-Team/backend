package com.softserve.skillscope.proof.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ProofCreationDto(
    @NotBlank
    String title,
    @NotBlank
    String description
){}
