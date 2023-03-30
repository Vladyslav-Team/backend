package com.softserve.skillscope.talent.service.interfaces;

import com.softserve.skillscope.talent.model.dto.JwtToken;
import com.softserve.skillscope.talent.model.dto.RegistrationRequest;

public interface AuthenticationService {
    JwtToken registration(RegistrationRequest registrationRequest);

    JwtToken login(String username);
}
