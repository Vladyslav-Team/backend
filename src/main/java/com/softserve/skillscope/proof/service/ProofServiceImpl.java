package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService {
    private ProofRepository proofRepo;
    private ProofMapper proofMapper;

    @Override
    public FullProof getFullProof(Long proofId) {
        return proofMapper.toFullProof(findProofById(proofId));
    }

    public ProofStatus setProofStatus(ProofStatus status) {
        for (ProofStatus validProofStatus : ProofStatus.values()) {
            if (validProofStatus == status) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }

    private Proof findProofById(Long proofId) {
        return proofRepo.findById(proofId)
                .orElseThrow(ProofNotFoundException::new);
    }
}
