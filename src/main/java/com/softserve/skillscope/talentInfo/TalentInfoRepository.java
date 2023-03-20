package com.softserve.skillscope.talentInfo;

import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentInfoRepository extends JpaRepository<TalentInfo, Integer> {
}
