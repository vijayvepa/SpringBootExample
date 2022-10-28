package com.vijayepa.aircraft.v2.stream;

import com.vijayepa.aircraft.v2.cacheacccess.AircraftRepository;
import com.vijayepa.aircraft.v2.model.Aircraft;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("SpellCheckingInspection")
@Configuration
public class AircraftConsumer {
    private final AircraftRepository aircraftRepository;

    public AircraftConsumer(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Bean
    Consumer<List<Aircraft>> retrieveAircraftPositions(){
        return aircrafts -> {
            aircraftRepository.deleteAll();
            aircraftRepository.saveAll(aircrafts);
            aircraftRepository.findAll().forEach(System.out::println);
        };
    }
}
