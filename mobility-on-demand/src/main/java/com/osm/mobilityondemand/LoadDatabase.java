package com.osm.mobilityondemand;

import com.osm.mobilityondemand.entity.Car;
import com.osm.mobilityondemand.entity.Demand;
import com.osm.mobilityondemand.entity.User;
import com.osm.mobilityondemand.repository.CarRepository;
import com.osm.mobilityondemand.repository.DemandRepository;
import com.osm.mobilityondemand.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CarRepository carRepository,
                                   UserRepository userRepository,
                                   DemandRepository demandRepository) {
        return args -> {
            carRepository.save(new Car("BMW","2.0","CUSTOM","SPECIAL","44.25"));
            carRepository.save(new Car("AUDI","1.6","CLASSIC","CLASSIC","52.14"));

            carRepository.findAll().forEach(car -> log.info("Preloaded: " + car));

            userRepository.save(new User("Osman","Male",24));
            userRepository.save(new User("Enes","Male",24));

            userRepository.findAll().forEach(user -> log.info("Preloaded: " + user));

            demandRepository.save(new Demand("44.25", "47.56", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
                    carRepository.findById(1L).orElseThrow(), userRepository.findById(3L).orElseThrow()));

            demandRepository.save(new Demand("52.14", "54.55", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
                    carRepository.findById(2L).orElseThrow(), userRepository.findById(4L).orElseThrow()));

            demandRepository.findAll().forEach(demand -> log.info("Preloaded: " + demand));

        };
    }
}
