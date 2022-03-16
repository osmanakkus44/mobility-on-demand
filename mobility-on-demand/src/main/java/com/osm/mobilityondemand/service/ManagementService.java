package com.osm.mobilityondemand.service;

import com.osm.mobilityondemand.repository.CarRepository;
import com.osm.mobilityondemand.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ManagementService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public ManagementService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public boolean isCarExist(Long id) {
        return carRepository.findById(id).orElse(null) != null;
    }

    public boolean isUserExist(Long id) {
        return userRepository.findById(id).orElse(null) != null;
    }

    public boolean isUserHasDemand(Long id) {
        return Objects.requireNonNull(userRepository.findById(id).orElse(null)).getDemand() != null;
    }

}
