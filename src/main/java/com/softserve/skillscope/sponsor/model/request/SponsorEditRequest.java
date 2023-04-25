package com.softserve.skillscope.sponsor.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Builder
public record SponsorEditRequest(
        @Size(min = 5, max = 64, message = "Password must be less than 64 characters and more than 5")
        String password,
        @Size(min = 1, max = 64, message = "Name must be less than 64 characters and more than 1")
        String name,
        @Size(min = 1, max = 64, message = "Surname must be less than 64 characters and more than 1")
        String surname,
        @URL
        String image,
        @Size(max = 32, message = "Location must be less than 32 characters")
        String location,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthday,
        @Size(max = 16, message = "Phone must be less than 16 characters")
        String phone
) {
}
