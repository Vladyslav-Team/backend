package com.softserve.skillscope.security.admin.service;

import com.softserve.skillscope.general.model.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AdminService {
    GeneralResponse createAdmin(HttpServletRequest request);

    GeneralResponse deleteUser(Long userId);

    GeneralResponse deleteProof(Long proofId);

    GeneralResponse lockRegistration();

    GeneralResponse unlockRegistration();

    void checkIfRegistrationIsLocked();
}
