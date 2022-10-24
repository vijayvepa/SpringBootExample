package com.vijayvepa.planereportermvc.controller;

import com.vijayvepa.planereportermvc.model.Aircraft;
import com.vijayvepa.planereportermvc.service.PositionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PositionController {

    @NonNull
    private final PositionService positionService;

    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model){
        final Iterable<Aircraft> currentAircraftPositions = positionService.getCurrentAircraftPositions();
        model.addAttribute("currentPositions", currentAircraftPositions);

        return "positions"; //.html
    }
}
