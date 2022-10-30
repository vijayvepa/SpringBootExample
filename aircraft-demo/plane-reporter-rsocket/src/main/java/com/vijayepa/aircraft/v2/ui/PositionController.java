package com.vijayepa.aircraft.v2.ui;

import com.vijayepa.aircraft.v2.cacheacccess.AircraftRepository;
import com.vijayepa.aircraft.v2.model.Aircraft;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Controller
public class PositionController {
    @NonNull
    private final AircraftRepository aircraftRepository;
    private final WebClient webClient
            = WebClient.create("http://localhost:7634/aircraft");
    private final RSocketRequester requester;

    public PositionController(
            @NonNull AircraftRepository aircraftRepository,
            RSocketRequester.Builder requesterBuilder) {
        this.aircraftRepository = aircraftRepository;
        this.requester = requesterBuilder.tcp("localhost", 7635);
    }

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

    private static void populateModel(
            Model model,
            Flux<Aircraft> aircraftFlux) {
       // final List<Aircraft> aircraftList = aircraftFlux.toStream().collect(Collectors.toList());
        model.addAttribute("currentPositions", aircraftFlux);
    }

    @ResponseBody
    @GetMapping(value = "/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentAircraftPositionsStream(){
        return requester.route("acstream").data("Requesting aircraft positions").retrieveFlux(Aircraft.class);
    }


}

