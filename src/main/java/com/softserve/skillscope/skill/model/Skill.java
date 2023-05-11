package com.softserve.skillscope.skill.model;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "proof_id", nullable = false)
    private List<Proof> proof;

    private String title;


}
