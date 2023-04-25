package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.TalentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentRepository extends JpaRepository<TalentInfo, Long> {
    Page<TalentInfo> findAllByOrderByIdDesc(Pageable pageable);
}
