package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.generalModel.generalResponse.GeneralResponse;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.ProofCreationDto;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;

import java.util.Optional;

public interface ProofService {
    FullProof getFullProof(Long proofId);
    GeneralProofResponse getAllProofByPage(Optional<Long> talentIdWrapper, int page, boolean newest);
    GeneralResponse addProof(Long talentId, ProofCreationDto creationRequest);
}
