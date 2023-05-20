package com.softserve.skillscope.kudos;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface KudosRepository extends JpaRepository<Kudos, Long> {

    Set<Kudos> findBySponsorAndProof(Sponsor sponsor, Proof proofId);
}
