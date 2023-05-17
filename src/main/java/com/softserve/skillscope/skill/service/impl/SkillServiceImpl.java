package com.softserve.skillscope.skill.service.impl;

import com.softserve.skillscope.skill.SkillRepository;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.skill.service.SkillService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService {
    private SkillRepository skillRepo;

    @Override
    public SkillResponse getAllSkillsWithFilter(String text) {
        Set<Skill> similarSkills = (text == null) ? skillRepo.findTop4ByOrderByTitleAsc() :
                skillRepo.findSimilarTitles(transformWord(text))
                        .stream()
                        .limit(4)
                        .collect(Collectors.toSet());
        return SkillResponse.builder()
                .skills(similarSkills)
                .build();
    }

    private String transformWord(String word) {
        return word.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining("%", "", "%"));
    }
}
