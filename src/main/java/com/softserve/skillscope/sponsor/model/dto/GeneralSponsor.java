package com.softserve.skillscope.sponsor.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GeneralSponsor(
        @NotBlank Long id,
        @NotBlank String image,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String location,
        @NotBlank String experience
) {
}
