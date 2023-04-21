package com.softserve.skillscope.kudos.model.enity;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kudos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "talent_id", nullable = true)
    private Talent talent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proof_id", nullable = false)
    private Proof proof;

}
