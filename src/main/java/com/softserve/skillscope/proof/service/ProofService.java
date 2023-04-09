package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.model.response.ProofResponse;

public interface ProofService {
    FullProof getFullProof(Long proofId);
    GeneralProofResponse getAllProofByPage(int page, boolean newest);
    ProofResponse deleteProofById(Long talentId, Long proofId);
}
