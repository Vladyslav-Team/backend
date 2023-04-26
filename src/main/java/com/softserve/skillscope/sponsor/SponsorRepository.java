package com.softserve.skillscope.sponsor;

import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    /*boolean existsByEmail(String email);

    Optional<Sponsor> findByEmail(String email);*/

    Page<Sponsor> findAllByOrderByIdDesc(Pageable pageable);
}
