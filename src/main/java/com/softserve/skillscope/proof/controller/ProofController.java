package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.dto.ProofCreationDto;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.service.ProofService;
import jakarta.validation.Valid;
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
        return proofService.getAllProofByPage(Optional.ofNullable(null), page, newest.orElse(true));
    }

    @GetMapping("/talents/{talent-id}/proofs")
    @ResponseStatus(HttpStatus.OK)
    public GeneralProofResponse showAllProofsByTalentId(@PathVariable("talent-id") Long talentId,
                                                        @RequestParam(defaultValue = "1") Optional<Integer> page,
                                                        @RequestParam(name = "newest") Optional<Boolean> newest) {
        return proofService.getAllProofByPage(Optional.of(talentId), page.orElse(1), newest.orElse(true));
    }

    @PostMapping("/talents/{talent-id}/proofs")
    @ResponseStatus(HttpStatus.CREATED)
    public Proof addProof(@PathVariable("talent-id") Long talentId,
                          @RequestBody @Valid ProofCreationDto creationRequest) {
        return proofService.addProof(talentId, creationRequest.title(), creationRequest.description());
    }
}
