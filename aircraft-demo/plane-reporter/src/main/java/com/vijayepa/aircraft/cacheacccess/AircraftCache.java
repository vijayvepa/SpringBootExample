package com.vijayepa.aircraft.cacheacccess;

import com.vijayepa.aircraft.model.Aircraft;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;

@Component
public class AircraftCache {

    private final WebClient webClient = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisOperations<String, Aircraft> redisOperations;


    public AircraftCache(
            RedisConnectionFactory redisConnectionFactory,
            RedisOperations<String, Aircraft> redisOperations) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisOperations = redisOperations;
    }

    public Set<String> getAllKeys() {
        return redisOperations.opsForValue()
                .getOperations()
                .keys("*");
    }

    public Aircraft getAircraft(String key) {
        return redisOperations.opsForValue().get(key);
    }

    public void save(Aircraft aircraft) {
        redisOperations.opsForValue().set(aircraft.getReg(), aircraft);
    }

    public void clear() {
        redisConnectionFactory.getConnection().serverCommands().flushDb();
    }
}
