package com.softserve.skillscope.talent.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.handler.exception.skillException.SkillNotFoundException;
import com.softserve.skillscope.general.mapper.talent.TalentMapper;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentStatsResponse;
import com.softserve.skillscope.talent.service.TalentService;
import com.softserve.skillscope.user.model.UserProperties;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class TalentServiceImpl implements TalentService {
    private UserProperties userProp;
    private TalentRepository talentRepo;
    private TalentMapper talentMapper;
    private PasswordEncoder passwordEncoder;
    private UtilService utilService;

    @Override
    public GeneralTalentResponse getAllTalentsByPage(int page, String skills) {
        try {
            PageRequest pageable = PageRequest.of(page - 1, userProp.userPageSize());

            Page<Talent> pageTalents = Optional.ofNullable(skills)
                    .filter(skill -> !skill.isBlank())
                    .map(skill -> {
                        Set<Skill> skillSet = utilService.parseAllSkills(skill);
                        return talentRepo.findTalentsBySkills(skillSet, skillSet.size(), pageable);
                    })
                    .orElseGet(() -> talentRepo.findAllByOrderByIdDesc(pageable));

            if (pageTalents.isEmpty()) throw new UserNotFoundException();

            int totalPages = pageTalents.getTotalPages();

            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }
            List<GeneralTalent> talents = new ArrayList<>(pageTalents.stream()
                    .map(talentMapper::toGeneralTalent)
                    .toList());

            return GeneralTalentResponse.builder()
                    .totalItems(pageTalents.getTotalElements())
                    .totalPage(totalPages)
                    .currentPage(page)
                    .talents(talents)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public TalentProfile getTalentProfile(Long talentId) {
        return talentMapper.toTalentProfile(utilService.findTalentById(talentId));
    }
    @Transactional
    @Override
    public GeneralResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate) {
        Talent talent = utilService.findTalentById(talentId);
        if (utilService.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }
        checkIfFieldsNotEmpty(talentToUpdate, talent);
        Talent saveTalent = talentRepo.save(talent);

        return new GeneralResponse(saveTalent.getId(), "Edited successfully!");
    }

    /*
     * This method returns String with talent's image, so frontend can draw the avatar.
     * Check if it's the image of own talent, in another case forbidden to get it.
     */
    @Override
    public ImageResponse getTalentImage(Long talentId) {
        return talentMapper.toTalentImage(utilService.findTalentById(talentId));
    }

    @Override
    public GeneralResponse addSkillsOnTalentProfile(Long talentId, AddSkillsRequest newSkillsRequest) {
        Talent talent = utilService.findTalentById(talentId);
        if (utilService.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }
        Set<Skill> newSkills = utilService.stringToSkills(newSkillsRequest.skills());
        talent.getSkills().addAll(newSkills);
        talent.setSkills(talent.getSkills());
        talentRepo.save(talent);

        return new GeneralResponse(talentId, "Skills successfully added!");
    }

    @Override
    public GeneralResponse deleteSkillFromTalentProfile(Long talentId, Long skillId) {
        Skill skill = utilService.findSkillById(skillId);
        Talent talent = utilService.findTalentById(talentId);
        if (utilService.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }
        if (talent.getSkills().size() < 1) {
            throw new BadRequestException("Talent cannot contain less than 0 Skills");
        }
        if (!talent.getSkills().contains(skill)) {
            throw new SkillNotFoundException();
        }
        talent.getSkills().remove(skill);
        talentRepo.save(talent);
        return new GeneralResponse(talentId, "Skill " + skill.getTitle() + " successfully deleted!");
    }

    @Override
    public TalentStatsResponse showOwnMostKudosProofs(Long talentId) {
        Talent talent = utilService.findTalentById(talentId);
        if (utilService.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }

        int maxTotalAmount = talent.getProofs().stream()
                .mapToInt(proof -> proof.getKudos().stream()
                        .mapToInt(Kudos::getAmount)
                        .sum())
                .max().getAsInt();

        return new TalentStatsResponse(talent.getProofs().stream()
                .filter(proof -> proof.getStatus() == ProofStatus.PUBLISHED)
                .filter(proof -> proof.getKudos().stream()
                        .mapToInt(Kudos::getAmount)
                        .sum() == maxTotalAmount)
                .map(Proof::getId)
                .toList());
    }

    @Override
    public TalentStatsResponse getOwnMostKudosedSkills(Long talentId) {

        Talent talent = utilService.findTalentById(talentId);
        if (utilService.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }

        int maxTotalAmount = talent.getProofs().stream()
                .flatMap(proof -> proof.getSkills().stream())
                .mapToInt(skill -> skill.getKudos().stream()
                        .filter(kudos -> talent.getProofs().contains(kudos.getProof()))
                        .mapToInt(Kudos::getAmount)
                        .sum())
                .max().getAsInt();

        return TalentStatsResponse.builder()
                .mostKudosedList(talent.getProofs().stream()
                        .filter(proof -> proof.getStatus() == ProofStatus.PUBLISHED)
                        .flatMap(proof -> proof.getSkills().stream())
                        .filter(skill -> skill.getKudos().stream()
                                .filter(kudos -> talent.getProofs().contains(kudos.getProof()))
                                .mapToInt(Kudos::getAmount).sum() != 0)
                        .filter(skill -> skill.getKudos().stream()
                                .filter(kudos -> talent.getProofs().contains(kudos.getProof()))
                                .mapToInt(Kudos::getAmount).sum() == maxTotalAmount)
                        .map(Skill::getId)
                        .toList()).build();
    }


    /*
     * This method checks the field for not null. If in request we didn't get that fields, don't edit them.
     */
    private void checkIfFieldsNotEmpty(TalentEditRequest talentToUpdate, Talent talent) {
        if (talentToUpdate == null) {
            throw new BadRequestException("No changes were applied");
        }
        talent.getUser().setName(utilService.validateField(talentToUpdate.name(), talent.getUser().getName()));
        talent.getUser().setSurname(utilService.validateField(talentToUpdate.surname(), talent.getUser().getSurname()));
        talent.setLocation(utilService.validateField(talentToUpdate.location(), talent.getLocation()));
        talent.setBirthday(talentToUpdate.birthday() != null ? talentToUpdate.birthday() : talent.getBirthday());
        if (talentToUpdate.password() != null) {
            talent.getUser().setPassword(
                    passwordEncoder.matches(talentToUpdate.password(), talent.getUser().getPassword())
                            ? talent.getUser().getPassword()
                            : passwordEncoder.encode(talentToUpdate.password())
            );
        }
        talent.setImage(utilService.validateField(talentToUpdate.image(), talent.getImage()));
        talent.setAbout(utilService.validateField(talentToUpdate.about(), talent.getAbout()));
        talent.setPhone(utilService.validateField(talentToUpdate.phone(), talent.getPhone()));
        talent.setExperience(utilService.validateField(talentToUpdate.experience(), talent.getExperience()));
        talent.setEducation(utilService.validateField(talentToUpdate.education(), talent.getEducation()));
    }
}
