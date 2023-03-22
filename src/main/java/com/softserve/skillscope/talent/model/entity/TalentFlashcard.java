package com.softserve.skillscope.talent.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record TalentFlashcard (
    Long id,
    String image,
    String name,
    String surname,
    String location
){}