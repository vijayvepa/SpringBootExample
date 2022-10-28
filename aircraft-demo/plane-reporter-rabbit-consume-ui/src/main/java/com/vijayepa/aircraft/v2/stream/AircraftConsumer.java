package com.vijayepa.aircraft.v2.stream;

import com.vijayepa.aircraft.v2.cacheacccess.AircraftRepository;
import com.vijayepa.aircraft.v2.model.Aircraft;
import com.vijayepa.aircraft.v2.websocket.WebSocketManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("SpellCheckingInspection")
@Configuration
public class AircraftConsumer {
    private final AircraftRepository aircraftRepository;
    private final WebSocketManager webSocketManager;

    public AircraftConsumer(AircraftRepository aircraftRepository,
                            WebSocketManager webSocketManager) {
        this.aircraftRepository = aircraftRepository;
        this.webSocketManager = webSocketManager;
    }

    @Bean
    Consumer<List<Aircraft>> retrieveAircraftPositions(){
        return aircrafts -> {
            aircraftRepository.deleteAll();
            aircraftRepository.saveAll(aircrafts);

            final Iterable<Aircraft> all = aircraftRepository.findAll();
            all.forEach(System.out::println);
            webSocketManager.notifyAll(new TextMessage(all.toString()));
        };
    }


}
