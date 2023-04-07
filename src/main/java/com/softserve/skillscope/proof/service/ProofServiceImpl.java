package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService{
    private ProofRepository proofRepo;

    public ProofStatus setProofStatus(ProofStatus status) {
        for(ProofStatus validProofStatus : ProofStatus.values()){
            if(validProofStatus == status){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status:" + status);
    }
}
