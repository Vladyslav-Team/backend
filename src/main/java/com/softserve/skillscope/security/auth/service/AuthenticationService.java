package com.softserve.skillscope.security.auth.service;

import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface AuthenticationService {
    String registrationTalent(RegistrationRequest request);

    String registerSponsor(RegistrationRequest request);
    String signInTalent(String username);

    String signInSponsor(String username);

    void signOutTalent(String details);
    void signOutSponsor(String details);
}
