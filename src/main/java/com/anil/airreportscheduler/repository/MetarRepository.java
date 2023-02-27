package com.anil.airreportscheduler.repository;

import com.anil.airreportscheduler.model.Metar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetarRepository extends JpaRepository<Metar,Long> {
}
