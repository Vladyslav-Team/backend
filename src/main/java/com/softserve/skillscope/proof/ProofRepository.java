package com.softserve.skillscope.proof;

import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProofRepository extends JpaRepository<Proof, Long>{
    List<Proof> findByTalentId(Long talentId);

    @Modifying
    @Query("DELETE FROM Proof WHERE id = ?1")
    void deleteById(Long proofId);
    Page<Proof> findAllByStatus(ProofStatus status, Pageable pageable);
    @Query("SELECT p FROM Proof p WHERE p.talent.id = ?1")
    Page<Proof> findByTalentId(Long id, Pageable pageable);

}
