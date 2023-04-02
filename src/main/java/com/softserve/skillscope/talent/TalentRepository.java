package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TalentRepository extends JpaRepository<Talent, Long> {

    boolean existsByEmail(String email);

    Optional<Talent> findByEmail(String email);

    Page<Talent> findAllByOrderByIdDesc(Pageable pageable);
}
