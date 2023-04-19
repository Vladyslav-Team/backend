package com.softserve.skillscope.kudos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Kudos {
    @EmbeddedId
    KudosKey id;

    @ManyToOne
    @MapsId("talentId")
    @JoinColumn(name = "talent_id")
    Talent talent;

    @ManyToOne
    @MapsId("proofId")
    @JoinColumn(name = "proof_id")
    Proof proof;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime time;
}
