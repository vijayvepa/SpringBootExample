package com.vijayvepa.plane;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

    public Flux<Aircraft> getAircraft() {
        List<Aircraft> positions = new ArrayList<>();


        try {
            loadPositionsFromUrl(positions);
        } catch (IOException e) {
            System.out.println(
                    "\n>>> IO Exception: " + e.getLocalizedMessage() +
                            ", generating and providing sample data.\n");
            return planeRepository.deleteAll().thenMany(saveSamplePositions());
        }

        if (positions.size() > 0) {
            positions.forEach(System.out::println);

            return planeRepository.deleteAll()
                    .thenMany(planeRepository.saveAll(positions));
        } else {
            System.out.println("\n>>> No positions to report, generating and providing sample data.\n");
            return planeRepository.deleteAll().thenMany(saveSamplePositions());
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

    private Flux<Aircraft> saveSamplePositions() {
        final Random rnd = new Random();

        return Flux.range(1, rnd.nextInt(10))
                .map(i-> flightGenerator.generate())
                .flatMap(planeRepository::save);
    }
}

