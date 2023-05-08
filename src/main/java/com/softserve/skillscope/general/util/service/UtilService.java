package com.softserve.skillscope.general.util.service;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.model.User;

import java.util.Map;
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

    Set<String> getRole(Set<Role> roles);
}
