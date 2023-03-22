package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TalentRepository extends JpaRepository<Talent, Long> {
    Talent findByEmail(String email);
    @Transactional
    @Modifying
    @Query("update Talent t set t.email = ?1 where t.email = ?2")
    void updateEmailByEmail(String email, String email1);
}
