package com.softserve.skillscope.proof;

import com.softserve.skillscope.proof.model.entity.Proof;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProofRepository extends JpaRepository<Proof, Long>{
    List<Proof> findByTalentId(Long talentId);
}
