package com.vijayepa.aircraft.polling;

import com.vijayepa.aircraft.model.Aircraft;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Optional;

@Component
@EnableScheduling
public class PlaneFinderPoller {

    private WebClient webClient = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisOperations<String, Aircraft> redisOperations;


    public PlaneFinderPoller(
            RedisConnectionFactory redisConnectionFactory,
            RedisOperations<String, Aircraft> redisOperations) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisOperations = redisOperations;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes(){
        redisConnectionFactory.getConnection().serverCommands().flushDb();

        webClient.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane-> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraft -> redisOperations.opsForValue().set(aircraft.getReg(), aircraft));

        redisOperations.opsForValue()
                .getOperations()
                .keys("*")

                .forEach(reg-> System.out.println(redisOperations.opsForValue().get(reg)));
    }
}


