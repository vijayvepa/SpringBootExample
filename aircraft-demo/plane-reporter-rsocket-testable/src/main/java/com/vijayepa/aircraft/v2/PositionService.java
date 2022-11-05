package com.vijayepa.aircraft.v2;


import com.vijayepa.aircraft.v2.model.Aircraft;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PositionService {
    private final AircraftRepository aircraftRepository;
    private final WebClient webClient = WebClient.create("http://localhost:7634/aircraft");

    public PositionService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public Flux<Aircraft> getAllAircraft(){
        return aircraftRepository.deleteAll().thenMany(
                webClient.get().retrieve()
                        .bodyToFlux(Aircraft.class)
                        .filter(plane -> !plane.getReg().isEmpty())
                        .flatMap(aircraftRepository::save)
        );
    }

    public Mono<Aircraft> getAircraftById(String id){
        return aircraftRepository.findById(id);
    }

    public Flux<Aircraft> getAircraftByReg(String id){
        return aircraftRepository.findAircraftByReg(id);
    }
}
