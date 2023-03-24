package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.TalentFlashcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TalentController {
    @Autowired
    private TalentService talentService;

    @GetMapping("/talents")
    public List<TalentFlashcard> showAllTalents(){
        return talentService.showAllTalents();
    }
}
