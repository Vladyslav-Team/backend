package com.softserve.skillscope.general.util.service;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.skill.model.dto.SkillWithVerification;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.model.User;

import java.util.Set;

public interface UtilService {
    Talent findTalentById(Long id);

    Sponsor findSponsorById(Long id);

    User findUserByEmail(String name);

    User findUserById(Long id);

    User getCurrentUser();

    boolean isNotCurrentUser(User user);

    Proof findProofById(Long proofId);

    String checkEmptyUserImage(RegistrationRequest request);

    String validateField(String requestField, String field);

    User createUser(RegistrationRequest request);

    String getRoles(User saveUser);

    Set<String> getRole(Set<Role> roles);

    User buildUser(RegistrationRequest request);

    boolean updateTokenActivation(Sponsor sponsor);

    Set<Skill> getSkillsByProofId(Long proofId);

    Skill findSkillById(Long id);

    Set<Skill> parseAllSkills(String skillsString);

    Set<Skill> stringToSkills(Set<String> newSet);

    Set<SkillWithVerification> getSkillsWithVerification(Talent talent);

    Integer calculateTotalKudosAmount4CurrentUser(Talent talent);

    boolean isCurrentKudos(Kudos kudos, User user);

    void checkIfKudosIsPresent(Integer amount, Sponsor sponsor, Proof proof, Skill skill);
}
