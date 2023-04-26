package com.softserve.skillscope.talent.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.user.Role;

import java.time.LocalDate;
import java.util.Set;

public record RegistrationRequest(
        String name,
        String surname,
        String email,
        String password,
        String location,
        String image,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthday,
        Set<Role> role
) {
}

