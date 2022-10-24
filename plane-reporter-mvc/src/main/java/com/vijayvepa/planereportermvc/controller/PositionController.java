package com.vijayvepa.planereportermvc.controller;

import com.vijayvepa.planereportermvc.dataaccess.AircraftRepository;
import com.vijayvepa.planereportermvc.model.Aircraft;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Controller
public class PositionController {

    @NonNull
    private final AircraftRepository aircraftRepository;
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model){
        aircraftRepository.deleteAll();

        client.get().retrieve().bodyToFlux(Aircraft.class).filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraftRepository::save);
        model.addAttribute("currentPositions", aircraftRepository.findAll());

        return "positions"; //.html
    }
}
