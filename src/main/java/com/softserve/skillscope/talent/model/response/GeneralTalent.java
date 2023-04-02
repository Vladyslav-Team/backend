package com.softserve.skillscope.talent.model.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GeneralTalent(
        @NotBlank Long id,
        @NotBlank String image,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String location,
        @NotBlank String experience
) {
}
