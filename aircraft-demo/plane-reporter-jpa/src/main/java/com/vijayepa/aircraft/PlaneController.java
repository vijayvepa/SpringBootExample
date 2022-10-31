package com.vijayepa.aircraft;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaneController {
    private final PlaneRetriever planeRetriever;

    public PlaneController(PlaneRetriever planeRetriever) {
        this.planeRetriever = planeRetriever;
    }

    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraftPositions() {
        return planeRetriever.retrieveAircraftPositions();
    }

}
