package com.softserve.skillscope.config.authentication;

import com.softserve.skillscope.talent.model.responce.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface AuthenticationService {
    JwtToken registration(RegistrationRequest registrationRequest);

    JwtToken login(String username);
}
