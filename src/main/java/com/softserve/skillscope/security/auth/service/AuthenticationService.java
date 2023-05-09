package com.softserve.skillscope.security.auth.service;

import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface AuthenticationService {
    JwtToken registrationTalent(RegistrationRequest request);

    JwtToken registerSponsor(RegistrationRequest request);

    JwtToken signIn(String username);

    void signOut(String details);
}
