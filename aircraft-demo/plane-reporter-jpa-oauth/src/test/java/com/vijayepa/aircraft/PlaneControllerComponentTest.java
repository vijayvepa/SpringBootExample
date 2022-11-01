package com.vijayepa.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p> No qualifying bean of type 'org.springframework.test.web.reactive.server.WebTestClient' available</p>
 * <p>Make sure RANDOM_PORT is selected</p>
 * <p><a href="https://stackoverflow.com/a/70623085/474377">StackOverflow</a></p>
 *
 * <p><b></b>Timeout on blocking read for 5000000000 NANOSECONDS</b></p>
 * <p>@AutoConfigureWebTestClient(timeout=10000)</p>
 * <p> <a href="https://mkyong.com/spring-boot/spring-webflux-test-timeout-on-blocking-read-for-5000-milliseconds"/>MKYong</a></p>
 */
@WebFluxTest(value = {PlaneController.class})
class PlaneControllerComponentTest {

    private Aircraft aircraft1, aircraft2;

    @MockBean
    PlaneRetriever positionRetriever;

    @Autowired
    WebTestClient client;

    @BeforeEach
    void setUp() {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        //   at 30000' currently over Kansas City
        aircraft1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL,
        //   at 40000' currently over Denver
        aircraft2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrentAircraftPositions() {
        Mockito.when(positionRetriever.retrieveAircraftPositions())
                .thenReturn(List.of(aircraft1, aircraft2));
        final Iterable<Aircraft> acPositions = client.get()
                .uri("/aircraft")
                .exchange()
                .expectStatus().isOk()
                //NOT .expectBody(Iterable.class)
                .expectBodyList(Aircraft.class)
                .returnResult()
                .getResponseBody();

        assertEquals(List.of(aircraft1, aircraft2), acPositions);
    }
}