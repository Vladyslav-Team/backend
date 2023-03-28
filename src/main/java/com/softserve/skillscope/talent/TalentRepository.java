package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TalentRepository extends JpaRepository<Talent, Long> {

    boolean existsByEmail(String email);

    Optional<Talent> findByEmail(String email);
}
