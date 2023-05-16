package com.softserve.skillscope.skill;

import com.softserve.skillscope.skill.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByProofsId(Long proofsId);
    List<Skill> findTop4ByOrderByTitleAsc();
    @Query(value = "SELECT s FROM Skill s WHERE UPPER(s.title) LIKE CONCAT('%', UPPER(:text), '%')")
    List<Skill> findSimilarTitles(String text);
}
