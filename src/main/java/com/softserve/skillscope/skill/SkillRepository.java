package com.softserve.skillscope.skill;

import com.softserve.skillscope.skill.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findAllByProofId(Long proofId);
    Skill findByTitle(String title);

}
