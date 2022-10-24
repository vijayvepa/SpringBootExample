package com.vijayvepa.plane.logic;

import com.vijayvepa.plane.model.Aircraft;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class FlightGenerator {
    private final Random random = new Random();

    List<String> typeList = List.of("A319", "A320", "A321", // Airbus
            "BE33", "BE36", // Beechcraft
            "B737", "B739", "B763", // Boeing
            "C172", "C402", "C560", // Cessna
            "E50P", "E75L", // Embraer
            "MD11", // McDonnell Douglas!
            "PA28", "PA32", "PA46"); // Piper

    Aircraft generate() {
        String callsignAndFlightNumber = "SAL" + random.nextInt(1000);

        final String reg = "N" + String.format("%1$5s", random.nextInt(10000)).replace(' ', '0');
        final String type = typeList.get(random.nextInt(typeList.size()));
        final int altitude = random.nextInt(40000);
        final int heading = random.nextInt(360);
        final Integer speed = random.ints(1, 100, 500).iterator().next();
        final float lat = random.doubles(1, 35d, 42d).iterator().next().floatValue();
        final float lon = random.doubles(1, -115d, -83d).iterator().next().floatValue();
        return new Aircraft(
                callsignAndFlightNumber,
                reg,
                callsignAndFlightNumber,
                type,
                altitude, // altitude
                heading,   // heading
                speed, // airspeed
                lat, // latitude
                lon);   // longitude
    }
}
