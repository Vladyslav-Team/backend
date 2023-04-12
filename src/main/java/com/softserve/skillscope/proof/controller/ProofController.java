package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.proof.model.ProofEditRequest;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.ProofCreationDto;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.service.ProofService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/talents/{talent-id}/proofs")
    public ResponseEntity<GeneralResponse> addProof(@PathVariable("talent-id") Long talentId,
                                                    @RequestBody @Valid ProofCreationDto creationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.addProof(talentId, creationRequest));
    }    

    @DeleteMapping("/talents/{talent-id}/proofs/{proof-id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse> deleteProofById(@PathVariable("talent-id") Long talentId,
                                                           @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.deleteProofById(talentId, proofId));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}")
    ResponseEntity<GeneralResponse> editProofById(@PathVariable("talent-id") Long talentId,
                                              @PathVariable("proof-id") Long proofId,
                                              @RequestBody @Valid ProofEditRequest proofToUpdate){
        return ResponseEntity.status(HttpStatus.OK).body(proofService.editProofById(talentId, proofId, proofToUpdate));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}/publish")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse> publishProofById(@PathVariable("talent-id") Long talentId,
                                                           @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.publishProofById(talentId, proofId));
    }
    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}/hide")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse> hideProofById(@PathVariable("talent-id") Long talentId,
                                                            @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.hideProofById(talentId, proofId));
    }
}
