package com.vijayvepa.planereportermvc.rabbit;

import com.vijayvepa.planereportermvc.model.Aircraft;
import com.vijayvepa.planereportermvc.service.PositionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class PositionReporter {
    private final PositionService positionService;

    public PositionReporter(PositionService positionService) {
        this.positionService = positionService;
    }

    @Bean
    Supplier<Iterable<Aircraft>> reportPositions(){
        return positionService::getCurrentAircraftPositions;
    }
}
