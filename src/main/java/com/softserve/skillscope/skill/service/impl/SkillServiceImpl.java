package com.softserve.skillscope.skill.service.impl;

import com.softserve.skillscope.skill.SkillRepository;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.skill.service.SkillService;
import com.softserve.skillscope.user.model.UserProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    private SkillRepository skillRepo;
    private UserProperties userProps;

    @Override
    public SkillResponse getAllSkillsWithFilter(java.lang.String text) {
        Set<Skill> similarSkills = getFilteredSkills(text);
        return SkillResponse.builder()
                .skills(similarSkills)
                .build();
    }

    public Set<Skill> getFilteredSkills(java.lang.String text) {
        return (text == null) ? skillRepo.findTop4ByOrderByTitleAsc() :
                skillRepo.findSimilarTitles(transformWord(text))
                        .stream()
                        .limit(userProps.maxSkillsOnTalent())
                        .collect(Collectors.toSet());
    }

    private String transformWord(String word) {
        return word.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining("%", "", "%"));
    }
}
