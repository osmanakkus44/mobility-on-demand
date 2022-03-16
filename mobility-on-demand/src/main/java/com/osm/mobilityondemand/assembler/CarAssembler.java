package com.osm.mobilityondemand.assembler;

import com.osm.mobilityondemand.controller.CarController;
import com.osm.mobilityondemand.entity.Car;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Component
public class CarAssembler implements RepresentationModelAssembler<Car, EntityModel<Car>> {
    @Override
    public EntityModel<Car> toModel(Car entity) {
        return EntityModel.of(
                entity,
                WebMvcLinkBuilder.linkTo(methodOn(CarController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(CarController.class).all()).withRel("cars")
        );
    }
}
