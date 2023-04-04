package com.softserve.skillscope.talent.controller;


import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;
import com.softserve.skillscope.talent.service.TalentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TalentController {
    private TalentService talentService;

    @GetMapping("/talents")
    @ResponseStatus(HttpStatus.OK)
    public GeneralTalentResponse showAllTalents(@RequestParam(defaultValue = "1") int page) {
        return talentService.getAllTalentsByPage(page);
    }

    @GetMapping("/talents/{talent-id}")
    @ResponseStatus(HttpStatus.OK)
    public TalentProfile showTalentProfile(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentProfile(talentId);
    }
    
     @DeleteMapping("/talents/{talent-id}")
    @ResponseBody
     TalentResponse delete(@PathVariable("talent-id") Long talentId) {
        return talentService.delete(talentId);
    }
}
