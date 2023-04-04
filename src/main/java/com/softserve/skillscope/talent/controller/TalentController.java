package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;
import com.softserve.skillscope.talent.service.TalentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TalentController {
    private TalentService talentService;

    @GetMapping("/talents")
    @ResponseStatus(HttpStatus.OK)
    GeneralTalentResponse showAllTalents(@RequestParam(defaultValue = "1") int page) {
        return talentService.getAllTalentsByPage(page);
    }

    @GetMapping("/talents/{talent-id}")
    @ResponseStatus(HttpStatus.OK)
    TalentProfile showTalentProfile(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentProfile(talentId);
    }

    @PatchMapping("/talents/{talent-id}")
    ResponseEntity<TalentResponse> editTalent(@PathVariable("talent-id") Long talentId,
                                              @RequestBody @Valid TalentEditRequest talentProfile) {
        return ResponseEntity.status(HttpStatus.OK).body(talentService.editTalentProfile(talentId, talentProfile));
    }
}
