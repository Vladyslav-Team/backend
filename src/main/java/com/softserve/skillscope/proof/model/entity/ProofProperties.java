package com.softserve.skillscope.proof.model.entity;

import com.softserve.skillscope.proof.model.response.ProofStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "proof")
public record ProofProperties(
        int proofPageSize,
        int concreteTalentProofPageSize,
        ProofStatus defaultType,
        String sortBy
) {
}
