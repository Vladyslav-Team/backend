package com.softserve.skillscope.proof;

import com.softserve.skillscope.proof.model.entity.Proof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProofRepository extends JpaRepository<Proof, Long>{
    List<Proof> findByTalentId(Long talentId);

    @Modifying
    @Query("DELETE FROM Proof WHERE id = :proof_id")
    void deleteById(@Param("proof_id") Long proofId);
}
