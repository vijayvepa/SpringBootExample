package com.vijayvepa.plane;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class PlaneController {
    private final PlaneFinderService planeFinderService;

    public PlaneController(PlaneFinderService planeFinderService) {
        this.planeFinderService = planeFinderService;
    }

    @ResponseBody
    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraft() throws IOException {
        return planeFinderService.getAircraft();
    }
}
