package com.softserve.skillscope.skill.service.impl;

import com.softserve.skillscope.general.handler.exception.skillException.SkillNotFoundException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.skill.SkillRepository;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.skill.service.SkillService;
import com.softserve.skillscope.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    private SkillRepository skillRepo;
    private UtilService utilService;

    @Override
    public SkillResponse getAllSkillsWithFilter(String text) {
        Set<Skill> similarSkills = getFilteredSkills(text);
        return SkillResponse.builder()
                .skills(similarSkills)
                .build();
    }

    public Set<Skill> getFilteredSkills(String text) {
        return (text == null) ? skillRepo.findTop4ByOrderByTitleAsc() :
                skillRepo.findSimilarTitles(transformWord(text))
                        .stream()
                        .limit(4)
                        .collect(Collectors.toSet());
    }

    private String transformWord(String word) {
        return word.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining("%", "", "%"));
    }

    @Override
    public KudosResponse showAmountKudosOfSkill(Long proofId, Long skillId) {
        User user = utilService.getCurrentUser();
        Proof proof = utilService.findProofById(proofId);
        Skill skill = utilService.findSkillById(skillId);
        if (!proof.getSkills().contains(skill)){
            throw new SkillNotFoundException();
        }
        int totalKudos = skill.getKudos().stream()
                .filter(kudos -> Objects.equals(kudos.getProof(), proof))
                .mapToInt(Kudos::getAmount)
                .sum();

        int currentUserKudos = skill.getKudos().stream()
                .filter(kudos -> Objects.equals(kudos.getProof(), proof))
                .filter(kudos -> kudos.getSponsor() != null)
                .filter(kudos -> user != null && utilService.isCurrentKudos(kudos, user))
                .mapToInt(Kudos::getAmount)
                .sum();
        return KudosResponse.builder()
                .proofId(proofId)
                .amountOfKudos(totalKudos)
                .amountOfKudosCurrentUser(currentUserKudos)
                .build();
    }
}
