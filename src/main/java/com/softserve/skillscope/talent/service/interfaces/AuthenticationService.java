package com.softserve.skillscope.talent.service.interfaces;

import com.softserve.skillscope.talent.model.dto.RegistrationRequest;

public interface AuthenticationService {
    String registration(RegistrationRequest registrationRequest);

    String login(String username);
}
