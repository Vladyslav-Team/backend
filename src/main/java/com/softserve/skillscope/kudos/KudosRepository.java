package com.softserve.skillscope.kudos;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KudosRepository extends JpaRepository<Kudos, Long> {

    Optional<Kudos> findByTalentAndProof(Talent talent, Proof proof);
}
