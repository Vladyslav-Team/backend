package com.softserve.skillscope.security.auth.controller;

import com.softserve.skillscope.security.auth.JwtToken;
import com.softserve.skillscope.security.auth.service.AuthenticationService;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping({"/talents", "/sponsors"})
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken registration(@Valid @RequestBody RegistrationRequest request) {
        return authenticationService.registration(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtToken signIn(@Valid Authentication authentication) {
        return authenticationService.signIn(authentication.getName());
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void signOut(Authentication authentication) {
        authenticationService.signOut(authentication.getName());
    }
}
