package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.kudos.model.request.KudosAmountRequest;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.request.ProofRequest;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.service.ProofService;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Proof", description = "API for Proof")
public class ProofController {
    private ProofService proofService;
    @GetMapping("/proofs/{proof-id}")
    @Operation(summary = "Get full proof")
    public FullProof showFullProof(@PathVariable("proof-id") Long proofId) {
        return proofService.getFullProof(proofId);
    }

    @GetMapping("/proofs")
    @Operation(summary = "Get all proofs")
    public GeneralProofResponse showAllProofs(@RequestParam(defaultValue = "1") int page, @RequestParam(name = "newest") Optional<Boolean> newest) {
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
    @Operation(summary = "Create proof")
    @PreAuthorize("hasRole('TALENT')")
    public ResponseEntity<GeneralResponse> addProof(@PathVariable("talent-id") Long talentId,
                                                    @RequestBody ProofRequest creationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.addProof(talentId, creationRequest));
    }

    @DeleteMapping("/talents/{talent-id}/proofs/{proof-id}")
    @Operation(summary = "Delete proof")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TALENT')")
    public ResponseEntity<GeneralResponse> deleteProofById(@PathVariable("talent-id") Long talentId,
                                                           @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.deleteProofById(talentId, proofId));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}")
    @Operation(summary = "Edit proof")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<GeneralResponse> editProofById(@PathVariable("talent-id") Long talentId,
                                                  @PathVariable("proof-id") Long proofId,
                                                  @RequestBody(required = false) ProofRequest proofToUpdate) {
        return ResponseEntity.status(HttpStatus.OK).body(proofService.editProofById(talentId, proofId, proofToUpdate));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}/publish")
    @Operation(summary = "Publish proof")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TALENT')")
    public ResponseEntity<GeneralResponse> publishProofById(@PathVariable("talent-id") Long talentId,
                                                            @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.publishProofById(talentId, proofId));
    }

    @PatchMapping("/talents/{talent-id}/proofs/{proof-id}/hide")
    @Operation(summary = "Hide proof")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TALENT')")
    public ResponseEntity<GeneralResponse> hideProofById(@PathVariable("talent-id") Long talentId,
                                                         @PathVariable("proof-id") Long proofId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.hideProofById(talentId, proofId));
    }

    @GetMapping("/proofs/{proof-id}/kudos")
    @Operation(summary = "Show amount Kudos of Proof")
    public ResponseEntity<KudosResponse> showAmountKudosOfProof(@PathVariable("proof-id") Long proofId){
        return ResponseEntity.status(HttpStatus.OK).body(proofService.showAmountKudosOfProof(proofId));
    }

    @PostMapping("/proofs/{proof-id}/kudos")
    @Operation(summary = "Add kudos to Proof")
    @PreAuthorize("hasRole('SPONSOR')")
    public ResponseEntity<GeneralResponse> addLikeToProof(@PathVariable("proof-id") Long proofId,
                                                          @RequestBody(required = false) KudosAmountRequest amount) {
        return ResponseEntity.status(HttpStatus.OK).body(proofService.addKudosToProofBySponsor(proofId, amount));
    }

    @GetMapping("/sponsors/{sponsor-id}/proofs")
    @Operation(summary = "Get all proofs by sponsorId")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('SPONSOR')")
    public GeneralProofResponse showAllProofsBySponsorId(@PathVariable("sponsor-id") Long sponsorId,
                                                        @RequestParam(defaultValue = "1") Optional<Integer> page,
                                                        @RequestParam(name = "newest") Optional<Boolean> newest) {
        return proofService.getAllProofByPage(Optional.of(sponsorId), page.orElse(1), newest.orElse(true));
    }

    @GetMapping("/proofs/{proof-id}/skills")
    @Operation(summary = "Get all skills by proof")
    public SkillResponse showAllSkillsByProof(@PathVariable("proof-id") Long proofId){
        return proofService.getAllSkillByProof(proofId);
    }

    @PostMapping("/talents/{talent-id}/proofs/{proof-id}/skills")
    @Operation(summary = "Add skills on proof")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<GeneralResponse> addSkillsOnProof(@PathVariable("talent-id") Long talentId,
                                                     @PathVariable("proof-id") Long proofId,
                                                     @RequestBody(required = false) AddSkillsRequest newSkills){
        return ResponseEntity.status(HttpStatus.OK).body(proofService.addSkillsOnProof(talentId, proofId, newSkills));
    }
}
