package com.osm.mobilityondemand.repository;

import com.osm.mobilityondemand.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
