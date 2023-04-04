package com.softserve.skillscope.config.authentication;

import com.softserve.skillscope.talent.model.response.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface AuthenticationService {
    JwtToken registration(RegistrationRequest registrationRequest);

    JwtToken login(String username);

    void logout(String details);
}
