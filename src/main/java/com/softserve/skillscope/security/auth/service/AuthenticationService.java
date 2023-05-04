package com.softserve.skillscope.security.auth.service;

import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface AuthenticationService {
    JwtToken registration(RegistrationRequest registrationRequest);

    JwtToken signIn(String username);

    void signOut(String details);
}