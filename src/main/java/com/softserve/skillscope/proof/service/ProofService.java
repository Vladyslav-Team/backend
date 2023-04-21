package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.proof.model.ProofEditRequest;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.ProofCreationDto;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import org.springframework.security.core.Authentication;

import java.util.Optional;


public interface ProofService {
    FullProof getFullProof(Long proofId);

    GeneralProofResponse getAllProofByPage(Optional<Long> talentIdWrapper, int page, boolean newest);

    GeneralResponse addProof(Long talentId, ProofCreationDto creationRequest);

    GeneralResponse deleteProofById(Long talentId, Long proofId);

    GeneralResponse editProofById(Long talentId, Long proofId, ProofEditRequest proofToUpdate);

    GeneralResponse publishProofById(Long talentId, Long proofId);

    GeneralResponse hideProofById(Long talentId, Long proofId);

    GeneralResponse addKudosToProofByTalent(Long proofId, Authentication authentication);
}
