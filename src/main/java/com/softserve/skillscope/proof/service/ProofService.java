package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;

public interface ProofService {
    ProofStatus setProofStatus(ProofStatus status);
}
