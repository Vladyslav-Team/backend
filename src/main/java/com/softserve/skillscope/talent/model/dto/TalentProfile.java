package com.softserve.skillscope.talent.model.dto;

import com.softserve.skillscope.skill.model.dto.SkillWithVerification;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;

@Builder
public record TalentProfile(
        @NotBlank Long id,
        @NotBlank String image,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String experience,
        @NotBlank String location,
        @NotBlank String about,
        @NotBlank String education,
        @NotBlank int age,
        @NotBlank String email,
        @NotBlank String phone,
        @NotBlank Set<SkillWithVerification> skills,
        @NotBlank Integer balance
) {
}
