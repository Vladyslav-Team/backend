package com.softserve.skillscope.general.util.service;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.skill.model.Skill;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.model.User;

import java.util.List;
import java.util.Set;

public interface UtilService {
    User findUserById(Long id);

    User findUserByEmail(String name);

    User getCurrentUser();

    boolean isNotCurrentUser(User user);

    Proof findProofById(Long proofId);

    String checkEmptyUserImage(RegistrationRequest request);

    String validateField(String requestField, String field);

    User createUser(RegistrationRequest request);

    String getRoles(User saveUser);

    Set<String> getRole(Set<Role> roles);

    boolean updateTokenActivation(Sponsor sponsor);

    List<Skill> getSkillsByProofId(Long proofId);
}
