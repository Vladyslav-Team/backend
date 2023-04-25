package com.softserve.skillscope.sponsor;

import com.softserve.skillscope.sponsor.model.entity.SponsorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<SponsorInfo, Long> {
}
