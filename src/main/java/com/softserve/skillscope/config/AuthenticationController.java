package com.softserve.skillscope.authentication;

import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.talent.model.response.JwtToken;
import com.softserve.skillscope.config.AuthenticationService;
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
