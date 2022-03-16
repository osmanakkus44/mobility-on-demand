package com.osm.mobilityondemand.repository;

import com.osm.mobilityondemand.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandRepository extends JpaRepository<Demand, Long> {
}
