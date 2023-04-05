package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.proof.ProofRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService{
    private ProofRepository proofRepo;
}
