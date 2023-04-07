package com.softserve.skillscope.proof.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import lombok.Builder;

import java.util.List;

@Builder
public record GeneralProofResponse(
    @JsonProperty("totalItems")
        long totalItems,
        @JsonProperty("totalPages")
        int totalPage,
        @JsonProperty("currentPage")
        int currentPage,
        List<GeneralProof> proofs) {
}
