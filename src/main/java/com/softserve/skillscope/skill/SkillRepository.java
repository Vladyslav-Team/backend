package com.softserve.skillscope.skill;

import com.softserve.skillscope.skill.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Set<Skill> findAllByProofsId(Long proofId);
    Skill findByTitle(String title);

    Set<Skill> findTop4ByOrderByTitleAsc();
    @Query(value = "SELECT s FROM Skill s WHERE UPPER(s.title) LIKE CONCAT('%', UPPER(:text), '%')")
    Set<Skill> findSimilarTitles(String text);
    Optional<Skill> findByTitleIgnoreCase(String title);
}
