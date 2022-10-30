package com.vijayepa.aircraft.v2.ui;

import com.vijayepa.aircraft.v2.cacheacccess.AircraftRepository;
import com.vijayepa.aircraft.v2.model.Aircraft;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class PositionController {
    @NonNull
    private final AircraftRepository aircraftRepository;
    private final WebClient webClient
            = WebClient.create("http://localhost:7634/aircraft");

    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model) {

        Flux<Aircraft> aircraftFlux = aircraftRepository.deleteAll().thenMany(
                webClient.get().retrieve()
                        .bodyToFlux(Aircraft.class)
                        .filter(plane -> !plane.getReg().isEmpty())
                        .flatMap(aircraftRepository::save)
        );


        populateModel(model, aircraftFlux);
        return "positions";
    }

    /**
     * @implNote
     * Error:
     * Caused by: org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'callsign' cannot be found on object of type 'reactor.core.publisher.FluxConcatArray' - maybe not public or not valid?
     * Fix:
     * Remove spring-web, spring-test, rebuild
     */
    private static void populateModel(
            Model model,
            Flux<Aircraft> aircraftFlux) {
       // final List<Aircraft> aircraftList = aircraftFlux.toStream().collect(Collectors.toList());
        model.addAttribute("currentPositions", aircraftFlux);
    }
}

