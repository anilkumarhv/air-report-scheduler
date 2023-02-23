package com.anil.airreportscheduler.repository;

import com.anil.airreportscheduler.model.Pirep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PirepRepository extends JpaRepository<Pirep, Long> {
}
