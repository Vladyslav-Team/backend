package com.softserve.skillscope.talent.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegistrationRequest(
        @NotEmpty
        @Size(min = 1, max = 64)
        @Pattern(regexp = "^[A-Za-z]+$",
                 message = "Name must be written in Latin")
        String name,
        @NotEmpty
        @Size(min = 1, max = 64)
        @Pattern(regexp = "^[A-Za-z]+$",
                 message = "Surname must be written in Latin")
        String surname,
        @NotEmpty
        @Size(min = 5, max = 254)
        @Email(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$",
                 message = "\"Not valid email. Must be examp@gmail.com")
        String email,
        @NotEmpty
        @Size(min = 8, max = 64)
        String password,
        @NotEmpty
        String location,
        @NotEmpty
        String image,
        @NotEmpty
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthday
) {}

