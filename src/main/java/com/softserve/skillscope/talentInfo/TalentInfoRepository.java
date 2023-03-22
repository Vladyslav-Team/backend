package com.softserve.skillscope.talentInfo;

import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TalentInfoRepository extends JpaRepository<TalentInfo, Long> {
    TalentInfo findByPhone(String phone);
    @Transactional
    @Modifying
    @Query("update TalentInfo t set t.phone = ?1 where t.phone = ?2")
    void updatePhoneByPhone(String phone, String phone1);
}
