package com.softserve.skillscope.security.auth.controller;

import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.security.auth.service.AuthenticationService;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "SponsorAuthentication", description = "API for Sponsor Authentication")
@RequestMapping("/sponsors")
public class SponsorAuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping
    @Operation(summary = "Sponsor Registration")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken registrationSponsor(@Valid @RequestBody RegistrationRequest request) {
        return authenticationService.registerSponsor(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Sponsor Login")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('SPONSOR')")
    public JwtToken signIn(@Valid Authentication authentication) {
        return authenticationService.signInSponsor(authentication.getName());
    }

    @GetMapping("/logout")
    @Operation(summary = "Sponsor Logout")
    @ResponseStatus(HttpStatus.OK)
    public void signOut(Authentication authentication) {
        authenticationService.signOutSponsor(authentication.getName());
    }
}
