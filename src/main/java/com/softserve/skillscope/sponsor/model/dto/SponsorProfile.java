package com.softserve.skillscope.sponsor.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SponsorProfile(
        @NotBlank Long id,
        @NotBlank String image,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String location,
        @NotBlank int age,
        @NotBlank String email,
        @NotBlank String phone,
        @NotBlank int balance
) {
}
