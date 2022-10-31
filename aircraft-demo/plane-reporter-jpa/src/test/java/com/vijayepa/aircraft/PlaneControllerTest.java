package com.vijayepa.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * <p> No qualifying bean of type 'org.springframework.test.web.reactive.server.WebTestClient' available</p>
 * <p>Make sure RANDOM_PORT is selected</p>
 * <p><a href="https://stackoverflow.com/a/70623085/474377">StackOverflow</a></p>
 *
 * <p><b></b>Timeout on blocking read for 5000000000 NANOSECONDS</b></p>
 * <p>@AutoConfigureWebTestClient(timeout=10000)</p>
 * <p> <a href="https://mkyong.com/spring-boot/spring-webflux-test-timeout-on-blocking-read-for-5000-milliseconds/>MKYong</a></p>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "1200000")
class PlaneControllerTest {

    @Autowired
    WebTestClient client;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrentAircraftPositions() {
        assert client.get()
                .uri("/aircraft")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Iterable.class)
                .returnResult()
                .getResponseBody()
                .iterator()
                .hasNext();
    }
}