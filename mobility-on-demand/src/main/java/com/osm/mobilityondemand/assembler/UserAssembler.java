package com.osm.mobilityondemand.assembler;

import com.osm.mobilityondemand.controller.UserController;
import com.osm.mobilityondemand.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User entity) {
        return EntityModel.of(
                entity,
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users")
                );
    }
}
