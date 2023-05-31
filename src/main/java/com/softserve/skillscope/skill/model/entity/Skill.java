package com.softserve.skillscope.skill.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
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

    @ManyToMany(mappedBy = "skills")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Set<Proof> proofs;

    @ManyToMany(mappedBy = "skills")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Set<Talent> talent;


    //Remove all kudos that connected to this skill.
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "skill", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Kudos> kudos;

    private String title;
}
