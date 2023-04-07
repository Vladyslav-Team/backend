package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService{
    private ProofRepository proofRepo;

    @Override
    public void setProofStatus(Proof proof, ProofStatus status) {
        if (Arrays.stream(ProofStatus.values()).noneMatch(s -> s == status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        proof.setStatus(status);
        proofRepo.save(proof);
    }
}
