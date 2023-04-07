package com.softserve.skillscope.proof.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FullProof(
        Long id,
        Long talentId,
        String talentName,
        String talentSurname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate publicationDate,
        String title,
        String description,
        ProofStatus status
) {
}
