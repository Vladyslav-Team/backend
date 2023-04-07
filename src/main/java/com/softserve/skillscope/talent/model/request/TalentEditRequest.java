package com.softserve.skillscope.talent.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Builder
public record TalentEditRequest(
        @NotBlank(message = "Password mustn't be blank ")
        @Size(min = 5, max = 64, message = "Password must be less than 64 characters and more than 5")
        String password,
        @NotBlank(message = "Name mustn't be blank ")
        @Size(min = 1, max = 64, message = "Name must be less than 64 characters and more than 1")
        String name,
        @NotBlank(message = "Surname mustn't be blank ")
        @Size(min = 1, max = 64, message = "Surname must be less than 64 characters and more than 1")
        String surname,
        String image,
        @Size(max = 254, message = "Experience must be less than 254 characters")
        String experience,
        @Size(max = 32, message = "Location must be less than 32 characters")
        String location,
        @Size(max = 1000, message = "AboutMe must be less than 1000 characters")
        String about,
        @Size(max = 64, message = "Education must be less than 64 characters")
        String education,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthday,
        @Size(max = 16, message = "Phone must be less than 16 characters")
        String phone
) {
}
