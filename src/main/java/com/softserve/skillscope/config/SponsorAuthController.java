package com.softserve.skillscope.authentication;

import com.softserve.skillscope.config.JwtToken;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/sponsors")
public class SponsorAuthController {
    SponsorAuthService sponsorAuthService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken registrationSponsor(@Valid @RequestBody RegistrationRequest request) {
        return sponsorAuthService.registrationSponsor(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtToken signInSponsor(@Valid Authentication authentication) {
        return sponsorAuthService.signInSponsor(authentication.getName());
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void signOutSponsor(Authentication authentication) {
        sponsorAuthService.signOutSponsor(authentication.getName());
    }
}
