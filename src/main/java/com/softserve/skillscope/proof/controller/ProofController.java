package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.service.ProofService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/proofs")
public class ProofController {
    private ProofService proofService;

    @GetMapping("{proof-id}")
    public FullProof showFullProof(@PathVariable("proof-id") Long proofId) {
        return proofService.getFullProof(proofId);
    }

    @GetMapping
    public GeneralProofResponse showAllProofs(@RequestParam(defaultValue = "1") int page, boolean newest){
        return proofService.getAllProofByPage(page, newest);
    }
}
