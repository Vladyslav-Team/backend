package com.softserve.skillscope.kudos;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KudosRepository extends JpaRepository<Kudos, Long> {
        //TODO @SEM rewrite the code for sponsor
    Optional<Kudos> findBySponsorAndProof(Sponsor sponsor, Proof proof);
}
