package com.softserve.skillscope.talent.model.dto;

import com.softserve.skillscope.skill.model.entity.Skill;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;

@Builder
public record GeneralTalent(
        @NotBlank Long id,
        @NotBlank String image,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String location,
        @NotBlank String experience,
        @NotBlank Set<Skill> skills
) {
}
