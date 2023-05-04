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
    @Query("SELECT p FROM Proof p WHERE p.status = 'PUBLISHED'")
    Page<Proof> findAllVisible(ProofStatus status, Pageable pageable);
    @Query("SELECT p FROM Proof p WHERE p.talent.id = ?1 and p.status = 'PUBLISHED'")
    Page<Proof> findAllVisibleByTalentId(Long id, ProofStatus status, Pageable pageable);

    @Query("SELECT p FROM Proof p JOIN Kudos k ON k.proof.id = p.id WHERE k.sponsor.id = ?1 AND p.status = 'PUBLISHED'")
    Page<Proof> findAllVisibleBySponsorId(Long sponsorId, ProofStatus status, Pageable pageable);


    @Query("SELECT p FROM Proof p WHERE p.talent.id = ?1")
    Page<Proof> findForCurrentTalent(Long id, Pageable pageable);

}
