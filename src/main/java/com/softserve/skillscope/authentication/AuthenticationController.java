package com.softserve.skillscope.authentication;

import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.talent.model.response.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "API for MyController")
@RequestMapping("/api/talents")
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
