package com.softserve.skillscope.authentication;

import com.softserve.skillscope.config.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;

public interface SponsorAuthService {
    JwtToken registrationSponsor(RegistrationRequest registrationRequest);

    JwtToken signInSponsor(String username);

    void signOutSponsor(String details);
}
