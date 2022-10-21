package com.vijayvepa.plane.reporter.mysql;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
class PlaneFinderPoller {
    @NonNull
    private final AircraftRepository repository;

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {

        repository.findAll().forEach(System.out::println);
    }
}
