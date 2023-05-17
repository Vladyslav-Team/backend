package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
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
}
