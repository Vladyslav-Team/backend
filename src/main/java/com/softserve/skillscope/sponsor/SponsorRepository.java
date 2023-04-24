package com.softserve.skillscope.sponsor;

import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
