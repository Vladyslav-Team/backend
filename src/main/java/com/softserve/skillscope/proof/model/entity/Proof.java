package com.softserve.skillscope.proof.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Proof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "talent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Talent talent;

    @Column(name = "publication_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime publicationDate;

    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private ProofStatus status;

//    //Remove all kudos that connected to this proof.
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "proof", cascade = CascadeType.ALL)
    private List<Kudos> kudos;

    @ManyToMany
    @JoinTable(name = "proof_skill",
            joinColumns = @JoinColumn(name = "proof_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<Skill> skills;
}
