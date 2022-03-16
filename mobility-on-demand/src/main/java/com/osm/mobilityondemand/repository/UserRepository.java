package com.osm.mobilityondemand.repository;

import com.osm.mobilityondemand.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
