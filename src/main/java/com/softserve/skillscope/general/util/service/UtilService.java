package com.softserve.skillscope.general.util.service;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.model.User;

public interface UtilService {
    User findUserById(Long id);

    User findUserByEmail(String name);

    User getCurrentUser();

    boolean isNotCurrentUser(User user);

    Proof findProofById(Long proofId);

    String checkEmptyUserImage(RegistrationRequest request);

    String validateField(String requestField, String field);
}
