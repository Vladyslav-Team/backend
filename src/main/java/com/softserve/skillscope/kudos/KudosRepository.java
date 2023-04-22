package com.softserve.skillscope.kudos;

import com.softserve.skillscope.kudos.model.enity.Kudos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudosRepository extends JpaRepository<Kudos, Long> {
}
