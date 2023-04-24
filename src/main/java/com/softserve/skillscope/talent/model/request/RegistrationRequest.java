package com.softserve.skillscope.talent.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.exception.generalException.ValidationException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegistrationRequest(
        @NotEmpty
        @Size(min = 1, max = 64)
        String name,
        @NotEmpty
        @Size(min = 1, max = 64)
        String surname,
        @NotEmpty
        @Size(min = 5, max = 254)
        String email,
        @NotEmpty
        @Size(min = 8, max = 64)
        String password,
        @NotEmpty
        String location,
        @NotEmpty
        String image,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthday
) {
        public RegistrationRequest(String name,
                                   String surname,
                                   String email,
                                   String password,
                                   String location,
                                   String image,
                                   LocalDate birthday) {
                this.name = name;
                this.surname = surname;
                this.email = email;
                this.password = password;
                this.location = location;
                this.image = image;
                this.birthday = birthday;

                String emailPattern = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
                String namePattern = "^[A-Za-z]+$";

                if (!name.matches(namePattern) || !surname.matches(namePattern)) {
                        throw new ValidationException("Name and surname both must be written in Latin");
                }
                else if (!email.matches(emailPattern)) {
                        throw new ValidationException("Email should be like example@domain.com");
                }
        }
}

