package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentStatsResponse;
import com.softserve.skillscope.talent.service.TalentService;
import com.softserve.skillscope.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Talent", description = "API for Talent")
@Slf4j
public class TalentController {
    private TalentService talentService;
    private UserService userService;

    @GetMapping("/talents")
    @Operation(summary = "Get all talents")
    @ResponseStatus(HttpStatus.OK)
    public GeneralTalentResponse showAllTalents(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(value = "skills", required = false) String skills) {
        return talentService.getAllTalentsByPage(page, skills);
    }

    @GetMapping("/talents/{talent-id}")
    @Operation(summary = "Get talent's profile")
    @ResponseStatus(HttpStatus.OK)
    public TalentProfile showTalentProfile(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentProfile(talentId);
    }

    @DeleteMapping("/talents/{talent-id}")
    @Operation(summary = "Delete talent")
    @PreAuthorize("hasRole('TALENT')")
    GeneralResponse delete(@PathVariable("talent-id") Long talentId) {
        return userService.deleteUser(talentId);
    }

    @PatchMapping("/talents/{talent-id}")
    @Operation(summary = "Edit talent")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<GeneralResponse> editTalent(Authentication authentication, @PathVariable("talent-id") Long talentId,
                                               @RequestBody(required = false) @Valid TalentEditRequest talentProfile) {
        log.info("auth={}", authentication);
        return ResponseEntity.status(HttpStatus.OK).body(talentService.editTalentProfile(talentId, talentProfile));
    }

    @GetMapping("/talent/image/{talent-id}")
    @Operation(summary = "Get image")
    @ResponseStatus(HttpStatus.OK)
    public ImageResponse showTalentImage(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentImage(talentId);
    }

    @PostMapping("/talents/{talent-id}/skills")
    @Operation(summary = "Add skills on talent profile")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<GeneralResponse> addSkillsOnTalentProfile(@PathVariable("talent-id") Long talentId,
                                                             @RequestBody(required = false) AddSkillsRequest newSkills) {
        return ResponseEntity.status(HttpStatus.OK).body(talentService.addSkillsOnTalentProfile(talentId, newSkills));
    }

    @DeleteMapping("/talents/{talent-id}/skills/{skill-id}")
    @Operation(summary = "Delete skills from talent profile")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<GeneralResponse> deleteSkillFromTalentProfile(@PathVariable("talent-id") Long talentId,
                                                                 @PathVariable("skill-id") Long skillId) {
        return ResponseEntity.status(HttpStatus.OK).body(talentService.deleteSkillFromTalentProfile(talentId, skillId));
    }
    @GetMapping("/talents/{talent-id}/stats/proofs")
    @Operation(summary = "Show the Talent's Most Kudos Proofs")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TALENT')")
    public TalentStatsResponse showOwnMostKudosProofs(@PathVariable("talent-id") Long talentId) {
        return talentService.showOwnMostKudosProofs(talentId);
    }

    @GetMapping("/talents/{talent-id}/stats/skills")
    @Operation(summary = "Get the most kudosed skill")
    @ResponseStatus(HttpStatus.OK)
    public TalentStatsResponse getOwnMostKudosedSkills(@PathVariable("talent-id") Long talentId) {
        return talentService.getOwnMostKudosedSkills(talentId);
    }
}
