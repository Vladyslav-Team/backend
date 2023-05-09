package com.softserve.skillscope.security.auth.controller;

import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.security.auth.service.AuthenticationService;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "TalentAuthentication", description = "API for Talent Authentication")
@RequestMapping("/talents")
public class TalentAuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping
    @Operation(summary = "Talent Registration")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken registrationTalent(@Valid @RequestBody RegistrationRequest request) {
        return authenticationService.registrationTalent(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Talent Login")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TALENT')")
    public JwtToken signIn(@Valid Authentication authentication) {
        return authenticationService.signIn(authentication.getName());
    }

    @GetMapping("/logout")
    @Operation(summary = "Talent Logout")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('TALENT')")
    public void signOut(Authentication authentication) {
        authenticationService.signOut(authentication.getName());
    }
}
