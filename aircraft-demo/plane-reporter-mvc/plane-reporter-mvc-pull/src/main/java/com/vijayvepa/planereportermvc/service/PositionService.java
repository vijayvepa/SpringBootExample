package com.vijayvepa.planereportermvc.service;

import com.vijayvepa.planereportermvc.dataaccess.AircraftRepository;
import com.vijayvepa.planereportermvc.model.Aircraft;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PositionService {

    @NonNull
    private final AircraftRepository aircraftRepository;
    private final WebClient client = WebClient.create("http://localhost:7634/aircraft");

    public Iterable<Aircraft> getCurrentAircraftPositions(){
        aircraftRepository.deleteAll();

        client.get().retrieve().bodyToFlux(Aircraft.class).filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraftRepository::save);
        return aircraftRepository.findAll();

    }
}
