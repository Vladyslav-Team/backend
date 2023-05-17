package com.softserve.skillscope.skill;

import com.softserve.skillscope.skill.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Set<Skill> findAllByProofsId(Long proofId);
    Skill findByTitle(String title);

}
