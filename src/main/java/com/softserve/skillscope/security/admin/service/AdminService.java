package com.softserve.skillscope.security.admin.service;

import com.softserve.skillscope.general.model.GeneralResponse;

public interface AdminService {
    GeneralResponse createAdmin();

    GeneralResponse deleteUser(Long userId);

    GeneralResponse deleteProof(Long proofId);

    GeneralResponse lockRegistration();

    GeneralResponse unlockRegistration();

    void checkIfRegistrationIsLocked();
}
