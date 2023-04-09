package com.softserve.skillscope.proof;

import com.softserve.skillscope.proof.model.entity.Proof;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProofRepository extends JpaRepository<Proof, Long>{
    List<Proof> findByTalentId(Long talentId);

    @Query("select p from Proof p where p.talent.id = ?1")
    Page<Proof> findByTalent_Id(Long id, Pageable pageable);

}
