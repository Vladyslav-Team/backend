package com.softserve.skillscope.skill.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.kudos.model.request.KudosAmountRequest;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.skill.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Skill", description = "API for Skill")
public class SkillController {
    private SkillService skillService;

    @GetMapping("/skills")
    @Operation(summary = "Get all skills")
    public SkillResponse showAllSkillsWithFilter(@RequestParam(name = "text", required = false) String text) {
        return skillService.getAllSkillsWithFilter(text);
    }

    @GetMapping("/proofs/{proof-id}/skills/{skill-id}/kudos")
    @Operation(summary = "Show amount Kudos of Skill")
    public ResponseEntity<KudosResponse> showAmountKudosOfSkill(@PathVariable("proof-id") Long proofId,
                                                                @PathVariable("skill-id") Long skillId) {
        return ResponseEntity.status(HttpStatus.OK).body(skillService.showAmountKudosOfSkill(proofId, skillId));
    }

    @PostMapping("/proofs/{proof-id}/skills/{skill-id}/kudos")
    @Operation(summary = "Add kudos to Skill on Proof")
    @PreAuthorize("hasRole('SPONSOR')")
    public ResponseEntity<GeneralResponse> addKudosToSkill(@PathVariable("proof-id") Long proofId,
                                                          @PathVariable("skill-id") Long skillId,
                                                          @RequestBody(required = false) KudosAmountRequest amount) {
        return ResponseEntity.status(HttpStatus.OK).body(skillService.addKudosToSkillBySponsor(proofId, skillId, amount));
    }
}
