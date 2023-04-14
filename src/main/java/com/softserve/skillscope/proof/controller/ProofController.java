package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.proof.model.ProofEditRequest;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.ProofCreationDto;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.service.ProofService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Proof", description = "API for MyController")
@AllArgsConstructor
public class ProofController {
    private ProofService proofService;

    @GetMapping("/proofs/{proof-id}")
    @Operation(summary = "Get full proof")
    public FullProof showFullProof(@PathVariable("proof-id") Long proofId) {
        return proofService.getFullProof(proofId);
    }

    @GetMapping("/proofs")
    @Operation(summary = "Get all proofs")
    public GeneralProofResponse showAllProofs(@RequestParam(defaultValue = "1") int page, @RequestParam(name = "newest") Optional<Boolean> newest){
        return proofService.getAllProofByPage(Optional.empty(), page, newest.orElse(true));
    }

    @GetMapping("/talents/{talent-id}/proofs")
    @Operation(summary = "Get all proofs by talentId")
    @ResponseStatus(HttpStatus.OK)
    public GeneralProofResponse showAllProofsByTalentId(@PathVariable("talent-id") Long talentId,
                                                        @RequestParam(defaultValue = "1") Optional<Integer> page,
                                                        @RequestParam(name = "newest") Optional<Boolean> newest) {
        return proofService.getAllProofByPage(Optional.of(talentId), page.orElse(1), newest.orElse(true));
    }


    @PostMapping("/talents/{talent-id}/proofs")
    @Operation(summary = "Crate proof")
    public ResponseEntity<GeneralResponse> addProof(@PathVariable("talent-id") Long talentId,
                                                    @RequestBody @Valid ProofCreationDto creationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.addProof(talentId, creationRequest));
    }    

    @DeleteMapping("/talents/{talent-id}/proofs/{proof-id}")
    @Operation(summary = "Delete proof")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse> deleteProofById(@PathVariable("talent-id") Long talentId,
                                                           @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.deleteProofById(talentId, proofId));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}")
    @Operation(summary = "Edit proof")
    ResponseEntity<GeneralResponse> editProofById(@PathVariable("talent-id") Long talentId,
                                              @PathVariable("proof-id") Long proofId,
                                              @RequestBody @Valid ProofEditRequest proofToUpdate){
        return ResponseEntity.status(HttpStatus.OK).body(proofService.editProofById(talentId, proofId, proofToUpdate));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}/publish")
    @Operation(summary = "Publish proof")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse> publishProofById(@PathVariable("talent-id") Long talentId,
                                                           @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.publishProofById(talentId, proofId));
    }
    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}/hide")
    @Operation(summary = "Hide proof")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse> hideProofById(@PathVariable("talent-id") Long talentId,
                                                            @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.hideProofById(talentId, proofId));
    }
}
