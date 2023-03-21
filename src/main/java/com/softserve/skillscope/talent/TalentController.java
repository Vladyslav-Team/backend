package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TalentController {

    @Autowired
    private TalentService talentService;

    @GetMapping("/talents")
    public List<Talent> showAllTalents(){
        return talentService.showAllTalents();
    }
}
