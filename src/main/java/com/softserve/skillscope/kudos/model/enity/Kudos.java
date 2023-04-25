package com.softserve.skillscope.kudos.model.enity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

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

    //TODO @SEM remove the code

    //    @ManyToOne
//    @JoinColumn(name = "talent_id")
//    private Talent talent;
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @ManyToOne
    @JoinColumn(name = "proof_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Proof proof;

    private Integer amount;
    @Column(name = "kudos_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm dd-MM-yyyy")
    private LocalDateTime kudosDate;
}
