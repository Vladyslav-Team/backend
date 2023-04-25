package com.softserve.skillscope.config;

import com.softserve.skillscope.talent.model.response.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface AuthenticationService {
    JwtToken registration(RegistrationRequest registrationRequest);

    JwtToken signIn(String username);

    void signOut(String details);
}
