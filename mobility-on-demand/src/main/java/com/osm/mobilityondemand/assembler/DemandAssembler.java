package com.osm.mobilityondemand.assembler;

import com.osm.mobilityondemand.controller.DemandController;
import com.osm.mobilityondemand.entity.Demand;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DemandAssembler implements RepresentationModelAssembler<Demand, EntityModel<Demand>> {
    @Override
    public EntityModel<Demand> toModel(Demand entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(DemandController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(DemandController.class).all()).withRel("demands")
        );
    }
}
