package com.softserve.skillscope.talent.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Talent {
    @Id
    @GeneratedValue
    private Integer id;

    private String email;
    private String password;
    private String name;
    private String surname;
}
