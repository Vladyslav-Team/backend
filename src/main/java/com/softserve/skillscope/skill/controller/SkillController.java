package com.softserve.skillscope.skill.controller;

import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.skill.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                                                @PathVariable("skill-id") Long skillId){
        return ResponseEntity.status(HttpStatus.OK).body(skillService.showAmountKudosOfSkill(proofId, skillId));
    }
}
