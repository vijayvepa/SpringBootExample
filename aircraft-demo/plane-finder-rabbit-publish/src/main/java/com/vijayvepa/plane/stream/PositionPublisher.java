package com.vijayvepa.plane.stream;

import com.vijayvepa.plane.logic.PlaneFinderService;
import com.vijayvepa.plane.model.Aircraft;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class PositionPublisher {

    private final PlaneFinderService planeFinderService;

    public PositionPublisher(PlaneFinderService planeFinderService) {
        this.planeFinderService = planeFinderService;
    }

    @Bean
    Supplier<Iterable<Aircraft>> reportPositions(){
        return planeFinderService::getAircraft;
    }

}
