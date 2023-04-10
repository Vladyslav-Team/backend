package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.model.response.ProofResponse;
import com.softserve.skillscope.proof.service.ProofService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProofController {
    private ProofService proofService;

    @GetMapping("/proofs/{proof-id}")
    public FullProof showFullProof(@PathVariable("proof-id") Long proofId) {
        return proofService.getFullProof(proofId);
    }

    @GetMapping("/proofs")
    public GeneralProofResponse showAllProofs(@RequestParam(defaultValue = "1") int page, @RequestParam(name = "newest") Optional<Boolean> newest){
        return proofService.getAllProofByPage(Optional.empty(), page, newest.orElse(true));
    }

    @GetMapping("/talents/{talent-id}/proofs")
    @ResponseStatus(HttpStatus.OK)
    public GeneralProofResponse showAllProofsByTalentId(@PathVariable("talent-id") Long talentId,
                                                        @RequestParam(defaultValue = "1") Optional<Integer> page,
                                                        @RequestParam(name = "newest") Optional<Boolean> newest) {
        return proofService.getAllProofByPage(Optional.of(talentId), page.orElse(1), newest.orElse(true));
    }

    @DeleteMapping("/talents/{talent-id}/proofs/{proof-id}")
    @ResponseStatus(HttpStatus.OK)
    public ProofResponse deleteProofById(@PathVariable("talent-id") Long talentId,
                                         @PathVariable("proof-id") Long proofId) {
        return proofService.deleteProofById(talentId, proofId);
    }
}
