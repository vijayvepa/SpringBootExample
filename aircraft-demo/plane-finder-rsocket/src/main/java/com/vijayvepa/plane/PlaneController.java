package com.vijayvepa.plane;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@Controller
public class PlaneController {
    private final PlaneFinderService planeFinderService;

    public PlaneController(PlaneFinderService planeFinderService) {
        this.planeFinderService = planeFinderService;
    }

    @ResponseBody
    @GetMapping("/aircraft")
    public Flux<Aircraft> getCurrentAircraft() throws IOException {
        return planeFinderService.getAircraft();
    }

    @MessageMapping("acstream")
    public Flux<Aircraft> getCurrentACStream() {
        return planeFinderService.getAircraft().concatWith(
            Flux.interval(Duration.ofSeconds(1)).flatMap(l-> planeFinderService.getAircraft())
        );
    }
}
