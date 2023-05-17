package com.softserve.skillscope.skill.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "skills", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<Proof> proofs;

    @ManyToMany(mappedBy = "skills", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<Talent> talent;

    private String title;
}
