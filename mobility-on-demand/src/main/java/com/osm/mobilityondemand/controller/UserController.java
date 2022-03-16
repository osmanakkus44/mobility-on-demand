package com.osm.mobilityondemand.controller;

import com.osm.mobilityondemand.assembler.UserAssembler;
import com.osm.mobilityondemand.entity.User;
import com.osm.mobilityondemand.repository.UserRepository;
import com.osm.mobilityondemand.service.ManagementService;
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
public class UserController {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    private final ManagementService managementService;

    public UserController(UserRepository userRepository, UserAssembler userAssembler, ManagementService managementService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.managementService = managementService;
    }


    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> one(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found" + id));

        return userAssembler.toModel(user);
    }

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User user) {
        EntityModel<User> entityModel = userAssembler.toModel(userRepository.save(user));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser = userRepository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setGender(newUser.getGender());
            user.setAge(newUser.getAge());
            user.setDemand(newUser.getDemand());
            return userRepository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return userRepository.save(newUser);
        });

        EntityModel<User> entityModel = userAssembler.toModel(updatedUser);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if(managementService.isUserExist(id) && managementService.isUserHasDemand(id)) {
            userRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
