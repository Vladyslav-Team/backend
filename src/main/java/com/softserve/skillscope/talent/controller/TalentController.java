package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentImageResponse;
import com.softserve.skillscope.talent.service.TalentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
@Tag(name = "Talent", description = "API for MyController")
@AllArgsConstructor
public class TalentController {
    private TalentService talentService;

    @GetMapping("/talents")
    @Operation(summary = "Get all talents")
    @ResponseStatus(HttpStatus.OK)
    public GeneralTalentResponse showAllTalents(@RequestParam(defaultValue = "1") int page) {
        return talentService.getAllTalentsByPage(page);
    }

    @GetMapping("/talents/{talent-id}")
    @Operation(summary = "Get talent's profile")
    @ResponseStatus(HttpStatus.OK)
    public TalentProfile showTalentProfile(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentProfile(talentId);
    }

    @DeleteMapping("/talents/{talent-id}")
    @Operation(summary = "Delete talent")
    @ResponseBody
    GeneralResponse delete(@PathVariable("talent-id") Long talentId) {
        return talentService.delete(talentId);
    }

    @PatchMapping("/talents/{talent-id}")
    @Operation(summary = "Edit talent")
    ResponseEntity<GeneralResponse> editTalent(@PathVariable("talent-id") Long talentId,
                                              @RequestBody @Valid TalentEditRequest talentProfile) {
        return ResponseEntity.status(HttpStatus.OK).body(talentService.editTalentProfile(talentId, talentProfile));
    }

    @GetMapping("/talent/image/{talent-id}")
    @Operation(summary = "Get image")
    @ResponseStatus(HttpStatus.OK)
    public TalentImageResponse showTalentImage(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentImage(talentId);
    }
}
