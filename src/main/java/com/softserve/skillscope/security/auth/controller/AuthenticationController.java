package com.softserve.skillscope.security.auth.controller;

import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.security.auth.service.AuthenticationService;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "API for Authentication")
@RequestMapping({"/talents", "/sponsors"})
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping
    @Operation(summary = "Registration")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken registration(@Valid @RequestBody RegistrationRequest request) {
        return authenticationService.registration(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ResponseStatus(HttpStatus.OK)
    public JwtToken signIn(@Valid Authentication authentication) {
        return authenticationService.signIn(authentication.getName());
    }

    @GetMapping("/logout")
    @Operation(summary = "Logout")
    @ResponseStatus(HttpStatus.OK)
    public void signOut(Authentication authentication) {
        authenticationService.signOut(authentication.getName());
    }
}
