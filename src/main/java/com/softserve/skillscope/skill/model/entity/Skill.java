package com.softserve.skillscope.skill.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.softserve.skillscope.proof.model.entity.Proof;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private List<Proof> proofs;

    private String title;
}
