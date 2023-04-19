package com.softserve.skillscope.kudos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class KudosKey implements Serializable {
    @Column(name = "talent_id")
    Long talentId;

    @Column(name = "proof_id")
    Long proofId;
}
