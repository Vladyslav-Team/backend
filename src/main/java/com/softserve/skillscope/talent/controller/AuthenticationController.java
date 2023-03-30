package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.talent.model.dto.RegistrationRequest;
import com.softserve.skillscope.talent.model.dto.JwtToken;
import com.softserve.skillscope.talent.service.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/talents")
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken registration(@Valid @RequestBody RegistrationRequest request) {
        return authenticationService.registration(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtToken login(Authentication authentication) {
        return authenticationService.login(authentication.getName());
    }
}