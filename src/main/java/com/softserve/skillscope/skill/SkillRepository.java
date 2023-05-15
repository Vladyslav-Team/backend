package com.softserve.skillscope.skill;

import com.softserve.skillscope.skill.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
