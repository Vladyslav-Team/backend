package com.softserve.skillscope.talent.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record RegistrationRequest (
    String name,
    String surname,
    String email,
    String password,
    String location,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth
)
{}
