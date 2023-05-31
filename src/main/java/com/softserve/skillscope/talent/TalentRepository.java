package com.softserve.skillscope.talent;

import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TalentRepository extends JpaRepository<Talent, Long> {
    Page<Talent> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT t FROM Talent t INNER JOIN t.skills s WHERE s IN :skills " +
            "GROUP BY t HAVING COUNT(DISTINCT s) = :numSkills")
    Page<Talent> findTalentsBySkills(
            @Param("skills") Set<Skill> skills,
            @Param("numSkills") int numSkills,
            Pageable pageable
    );
}
