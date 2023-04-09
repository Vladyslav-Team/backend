package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
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
    public GeneralProofResponse showAllProofs(@RequestParam(defaultValue = "1") int page, boolean newest) {
        return proofService.getAllProofByPage(page, newest);
    }

    @GetMapping("/talents/{talent-id}/proofs")
    @ResponseStatus(HttpStatus.OK)
    public GeneralProofResponse showAllProofsByTalentId(@PathVariable("talent-id") Long talentId,
                                                        @RequestParam(defaultValue = "1") Optional<Integer> page,
                                                        Optional<Boolean> sortByNewest) {
        return proofService.getAllProofByPage(page.orElse(1), sortByNewest.orElse(true));
    }
}
