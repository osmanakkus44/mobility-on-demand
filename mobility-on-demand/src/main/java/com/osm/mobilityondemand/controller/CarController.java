package com.osm.mobilityondemand.controller;

import com.osm.mobilityondemand.assembler.CarAssembler;
import com.osm.mobilityondemand.entity.Car;
import com.osm.mobilityondemand.repository.CarRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CarController {

    private final CarRepository carRepository;
    private final CarAssembler carAssembler;

    public CarController(CarRepository carRepository, CarAssembler carAssembler) {
        this.carRepository = carRepository;
        this.carAssembler = carAssembler;
    }

    @GetMapping("/cars")
    public CollectionModel<EntityModel<Car>> all() {
        List<EntityModel<Car>> cars = carRepository.findAll().stream()
                .map(carAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cars, linkTo(methodOn(CarController.class).all()).withSelfRel());
    }

    @GetMapping("/cars/{id}")
    public EntityModel<Car> one(@PathVariable Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car Not Found" + id));

        return carAssembler.toModel(car);
    }

    @PostMapping("/cars")
    public ResponseEntity<?> newCar(@RequestBody Car car) {
        EntityModel<Car> entityModel = carAssembler.toModel(carRepository.save(car));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<?> updateCar(@RequestBody Car newCar, @PathVariable Long id) {
        Car updatedCar = carRepository.findById(id).map(car -> {
            car.setEngine(newCar.getEngine());
            car.setCurrentLocation(newCar.getCurrentLocation());
            car.setInfotainmentSystem(newCar.getInfotainmentSystem());
            car.setInteriorDesign(newCar.getInteriorDesign());
            car.setModel(newCar.getModel());
            car.setDemand(newCar.getDemand());
            return carRepository.save(car);
        }).orElseGet(() -> {
            newCar.setId(id);
            return carRepository.save(newCar);
        });

        EntityModel<Car> entityModel = carAssembler.toModel(updatedCar);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/cars/{id}/location")
    public ResponseEntity<?> updateLocation(@RequestParam String location, @PathVariable Long id) {
        Car updatedCar = carRepository.findById(id).map(car -> {
            car.setCurrentLocation(location);
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car Not Found" + id));

        EntityModel<Car> entityModel = carAssembler.toModel(updatedCar);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
