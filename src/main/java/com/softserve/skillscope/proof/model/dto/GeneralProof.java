package com.softserve.skillscope.proof.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GeneralProof(
        @NotBlank Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm dd-MM-yyyy")
        LocalDateTime publicationDate,
        String title,
        String description,
        @NotBlank ProofStatus status
) {
}
