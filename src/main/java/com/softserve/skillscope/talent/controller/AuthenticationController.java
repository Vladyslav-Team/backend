package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.talent.model.dto.RegistrationRequest;
import com.softserve.skillscope.talent.service.interfaces.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/talents")
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping()
    public String registration(RegistrationRequest request) {
        return authenticationService.registration(request);
    }

    @PostMapping("/login")
    public String login(Authentication authentication) {
        return authenticationService.login(authentication.getName());
    }
}
