package com.vijayepa.aircraft;

import com.vijayepa.aircraft.v2.PositionService;
import com.vijayepa.aircraft.v2.model.Aircraft;
import com.vijayepa.aircraft.v2.ui.PositionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = PositionController.class)
@WebFluxTest(controllers = {PositionController.class})
public class PositionControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PositionService positionService;

    @MockBean
    private RSocketRequester requester;

    private Aircraft ac1, ac2, ac3;
    private final String id1 = UUID.randomUUID().toString();
    private final String id2 = UUID.randomUUID().toString();
    private final String id3 = UUID.randomUUID().toString();


    @BeforeEach
    void setUp(ApplicationContext context) {

        // Spring Airlines flight 001 en route, flying STL to SFO,
        // at 30000' currently over Kansas City
        ac1 = new Aircraft(
                id1, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL,
        // at 40000' currently over Denver
        ac2 = new Aircraft(id2, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Spring Airlines flight 002 en route, flying SFO to STL,
        // at 40000' currently just past DEN
        ac3 = new Aircraft(id3, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8412964, -105.0048267, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        reset(positionService);
        when(positionService.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3));
        when(positionService.getAircraftById(id1)).thenReturn(Mono.just(ac1));
        when(positionService.getAircraftById(id2)).thenReturn(Mono.just(ac2));
        when(positionService.getAircraftById(id3)).thenReturn(Mono.just(ac3));
        when(positionService.getAircraftByReg("N12345")).thenReturn(Flux.just(ac1));
        when(positionService.getAircraftByReg("N54321")).thenReturn(Flux.just(ac2, ac3));

        //Hooks.onOperatorDebug();

    }

    @Test
    void getCurrentACPositions() {
        StepVerifier.create(client.get().uri("/acpos").exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody()
                ).expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();

    }

    @Test
    void getCurrentACPositions_ErrorCheckpoint() {


        when(positionService.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3).concatWith(Flux.error(new Throwable("Bad position report")))
                .checkpoint());
        StepVerifier.create(client.get().uri("/acpos").exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody()
                ).expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();

    }
    @Test
    void getCurrentACPositions_Error() {


        when(positionService.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3).concatWith(Flux.error(new Throwable("Bad position report"))));
        StepVerifier.create(client.get().uri("/acpos").exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody()
                ).expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();

    }

    @Test
    void getCurrentACPositions_ErrorHook() {

        Hooks.onOperatorDebug();

        when(positionService.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3).concatWith(Flux.error(new Throwable("Bad position report"))));
        StepVerifier.create(client.get().uri("/acpos").exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody()
                ).expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();

        Hooks.resetOnOperatorDebug();

    }

    @Test
    void getCurrentACPositions_ErrorWithDetails() {


        when(positionService.getAllAircraft()).thenReturn(
                Flux.just(ac1, ac2, ac3)
                        .checkpoint("All aircraft: after all good positions reported.")
                        .concatWith(Flux.error(new Throwable("Bad position report")))
                .checkpoint("All aircraft: after appending bad position error"));
        StepVerifier.create(client.get().uri("/acpos").exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody()
                ).expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();

    }

    @Test
    void getCurrentACPositionsById() {
        StepVerifier.create(client.get().uri("/acpos/search?id=" + id1).exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody()
                ).expectNext(ac1)
                .verifyComplete();

    }

    @Test
    void getCurrentACPositionsByReg() {
        StepVerifier.create(client.get().uri("/acpos/search?reg=N54321").exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody())
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();

    }
}
