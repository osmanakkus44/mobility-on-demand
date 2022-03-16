package com.osm.mobilityondemand.service;

import com.osm.mobilityondemand.repository.DemandRepository;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    private final DemandRepository demandRepository;


    public SchedulingService(DemandRepository demandRepository) {
        this.demandRepository = demandRepository;
    }

    public boolean scheduleService() {
        return true;
    }

}
