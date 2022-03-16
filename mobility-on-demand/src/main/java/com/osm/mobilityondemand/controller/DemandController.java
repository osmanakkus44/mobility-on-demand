package com.osm.mobilityondemand.controller;

import com.osm.mobilityondemand.assembler.DemandAssembler;
import com.osm.mobilityondemand.entity.Demand;
import com.osm.mobilityondemand.repository.DemandRepository;
import com.osm.mobilityondemand.service.ManagementService;
import com.osm.mobilityondemand.service.SchedulingService;
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
public class DemandController {
    private final DemandRepository demandRepository;
    private final DemandAssembler demandAssembler;

    private final ManagementService managementService;
    private final SchedulingService schedulingService;

    public DemandController(DemandRepository demandRepository, DemandAssembler demandAssembler,
        ManagementService managementService,
        SchedulingService schedulingService) {
        this.demandRepository = demandRepository;
        this.demandAssembler = demandAssembler;
        this.managementService = managementService;
        this.schedulingService = schedulingService;
    }

    @GetMapping("/demands")
    public CollectionModel<EntityModel<Demand>> all() {
        List<EntityModel<Demand>> demands = demandRepository.findAll().stream()
                .map(demandAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(demands, linkTo(methodOn(DemandController.class).all()).withSelfRel());
    }

    @GetMapping("/demands/{id}")
    public EntityModel<Demand> one(@PathVariable Long id) {
        Demand demand = demandRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found" + id));

        return demandAssembler.toModel(demand);
    }


    @PostMapping("/demands")
    public ResponseEntity<?> newDemand(@RequestBody Demand demand) {

        if (!managementService.isCarExist(demand.getCar().getId())) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Demand> entityModel = demandAssembler.toModel(demandRepository.save(demand));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/demands/{id}")
    public ResponseEntity<?> updateDemand(@RequestBody Demand newDemand, @PathVariable Long id) {
        Demand updatedDemand = demandRepository.findById(id).map(demand -> {
            demand.setDropOffLocation(newDemand.getDropOffLocation());
            demand.setPickUpLocation(newDemand.getPickUpLocation());
            demand.setDropOffTime(newDemand.getDropOffTime());
            demand.setPickUpTime(newDemand.getPickUpTime());
            demand.setUser(newDemand.getUser());
            demand.setCar(newDemand.getCar());
            return demandRepository.save(demand);
        }).orElseGet(() -> {
            newDemand.setId(id);
            return demandRepository.save(newDemand);
        });

        EntityModel<Demand> entityModel = demandAssembler.toModel(updatedDemand);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/demands/{id}")
    public ResponseEntity<?> deleteDemand(@PathVariable Long id) {
        demandRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
