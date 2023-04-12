package com.softserve.skillscope.talent.model.entity;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Talent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "talent", cascade = CascadeType.ALL)
    private TalentInfo talentInfo;

    @NotEmpty
    @Size(min = 5, max = 254)
    private String email;

    @NotEmpty
    @Size(min = 8, max = 64)
    private String password;

    @NotEmpty
    @Size(min = 1, max = 64)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 64)
    private String surname;

    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL)
    private List<Proof> proofs;
}
