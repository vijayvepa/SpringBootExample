package com.vijayepa.aircraft.v2.polling;

import com.vijayepa.aircraft.v2.cacheacccess.AircraftRepository;
import com.vijayepa.aircraft.v2.webaccess.AircraftClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PlaneFinderPoller {

    private final AircraftRepository aircraftRepository;

    private final AircraftClient aircraftClient;


    public PlaneFinderPoller(
            AircraftRepository aircraftRepository,
            AircraftClient aircraftClient) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftClient = aircraftClient;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
//        aircraftRepository.deleteAll();

//        aircraftClient.getAircrafts()
//                .forEach(aircraftRepository::save);

        aircraftRepository.findAll()
                .forEach(System.out::println);
    }
}


