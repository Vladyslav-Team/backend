package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<Talent, Long> {
}
