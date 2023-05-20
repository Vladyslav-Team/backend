package com.softserve.skillscope.skill.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.skillException.SkillNotFoundException;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.KudosRepository;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.kudos.model.request.KudosAmountRequest;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.skill.SkillRepository;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.skill.service.SkillService;
import com.softserve.skillscope.sponsor.SponsorRepository;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.user.model.User;
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
    private UtilService utilService;
    private KudosRepository kudosRepo;
    private SponsorRepository sponsorRepo;

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

    @Override
    public KudosResponse showAmountKudosOfSkill(Long proofId, Long skillId) {
        Proof proof = utilService.findProofById(proofId);
        Skill skill = utilService.findSkillById(skillId);
        if (!proof.getSkills().contains(skill)) {
            throw new SkillNotFoundException();
        }
        User user = utilService.getCurrentUser();
        int totalKudos = skill.getKudos().stream()
                .mapToInt(Kudos::getAmount)
                .sum();

        int currentUserKudos = skill.getKudos().stream()
                .filter(kudos -> kudos.getSponsor() != null)
                .filter(kudos -> user != null && kudos.getSponsor().getId().equals(user.getId()))
                .mapToInt(Kudos::getAmount)
                .sum();
        return KudosResponse.builder()
                .proofId(proofId)
                .amountOfKudos(totalKudos)
                .amountOfKudosCurrentUser(currentUserKudos)
                .build();
    }

    @Override
    public GeneralResponse addKudosToSkillBySponsor(Long proofId, Long skillId, KudosAmountRequest kudosAmountRequest) {
        if (kudosAmountRequest == null || kudosAmountRequest.amount() < 1) {
            throw new BadRequestException("Amount of Kudos must not be less than 1!");
        }
        Integer amount = kudosAmountRequest.amount();
        Sponsor sponsor = utilService.getCurrentUser().getSponsor();
        if (sponsor.getBalance() < amount) {
            throw new BadRequestException("Not enough kudos on the balance sheet");
        }
        Proof proof = utilService.findProofById(proofId);
        Skill skill = utilService.findSkillById(skillId);
        utilService.checkIfKudosIsPresent(amount, sponsor, proof, skill);
        sponsor.setBalance(sponsor.getBalance() - amount);
        sponsorRepo.save(sponsor);
        return new GeneralResponse(proof.getId(), amount + " kudos was added successfully!");
    }
}
