package com.softserve.skillscope.talentInfo;

import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TalentInfoRepository extends JpaRepository<TalentInfo, Long> {

}
