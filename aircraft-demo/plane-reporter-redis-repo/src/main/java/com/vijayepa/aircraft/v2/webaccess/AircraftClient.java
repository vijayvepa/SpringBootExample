package com.vijayepa.aircraft.v2.webaccess;

import com.vijayepa.aircraft.v2.model.Aircraft;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Stream;

@Component
public class AircraftClient {

    private final WebClient webClient = WebClient.create("http://localhost:7634/aircraft");

    public Stream<Aircraft> getAircrafts() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream();
    }
}
