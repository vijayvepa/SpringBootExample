package com.vijayvepa.plane;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlaneFinderService {
    private final PlaneRepository planeRepository;
    private final FlightGenerator flightGenerator;
    private final URL aircraftUrl;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public PlaneFinderService(
            PlaneRepository planeRepository,
            FlightGenerator flightGenerator) {

        this.planeRepository = planeRepository;
        this.flightGenerator = flightGenerator;

        aircraftUrl = new URL("http://192.168.1.139/ajax/aircraft");
        objectMapper = new ObjectMapper();
    }

    public Iterable<Aircraft> getAircraft() {
        List<Aircraft> positions = new ArrayList<>();


        try {
            loadPositionsFromUrl(positions);
        } catch (IOException e) {
            System.out.println(
                    "\n>>> IO Exception: " + e.getLocalizedMessage() +
                            ", generating and providing sample data.\n");
            return saveSamplePositions();
        }

        if (positions.size() > 0) {
            positions.forEach(System.out::println);

            planeRepository.deleteAll();
            return planeRepository.saveAll(positions);
        } else {
            System.out.println("\n>>> No positions to report, generating and providing sample data.\n");
            return saveSamplePositions();
        }
    }

    private void loadPositionsFromUrl(List<Aircraft> positions) throws IOException {
        JsonNode aircraftNodes = objectMapper.readTree(aircraftUrl)
                .get("aircraft");

        aircraftNodes.iterator().forEachRemaining(node -> {
            try {
                positions.add(objectMapper.treeToValue(node, Aircraft.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private Iterable<Aircraft> saveSamplePositions() {
        final Random rnd = new Random();

        planeRepository.deleteAll();

        for (int i = 0; i < rnd.nextInt(10); i++) {
            planeRepository.save(flightGenerator.generate());
        }

        return planeRepository.findAll();
    }
}

