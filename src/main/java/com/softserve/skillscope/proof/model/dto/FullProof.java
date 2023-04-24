package com.softserve.skillscope.proof.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FullProof(
        Long id,
        Long talentId,
        String talentName,
        String talentSurname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm dd-MM-yyyy")
        LocalDateTime publicationDate,
        String title,
        String description,
        ProofStatus status
) {
}
