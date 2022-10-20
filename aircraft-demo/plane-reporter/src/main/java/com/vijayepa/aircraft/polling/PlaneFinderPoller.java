package com.vijayepa.aircraft.polling;

import com.vijayepa.aircraft.cacheacccess.AircraftCache;
import com.vijayepa.aircraft.webaccess.AircraftClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PlaneFinderPoller {

    private final AircraftCache aircraftCache;

    private final AircraftClient aircraftClient;


    public PlaneFinderPoller(
            AircraftCache aircraftCache,
            AircraftClient aircraftClient) {
        this.aircraftCache = aircraftCache;
        this.aircraftClient = aircraftClient;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        aircraftCache.clear();

        aircraftClient.getAircrafts()
                .forEach(aircraftCache::save);

        aircraftCache.getAllKeys()
                .forEach(key -> System.out.println(aircraftCache.getAircraft(key)));
    }
}


