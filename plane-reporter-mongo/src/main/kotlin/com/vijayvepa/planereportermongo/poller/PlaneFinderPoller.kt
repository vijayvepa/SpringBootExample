package com.vijayvepa.planereportermongo.poller

import com.vijayvepa.planereportermongo.dataaccess.AircraftRepository
import com.vijayvepa.planereportermongo.model.Aircraft
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
@EnableScheduling
class PlaneFinderPoller(private val repository: AircraftRepository) {
    private val client = WebClient.create("http://localhost:7634/aircraft")

    @Scheduled(fixedRate = 1000)
    private fun pollPlanes() {
        repository.deleteAll();
        client.get().retrieve().bodyToFlux(Aircraft::class.java)
            .filter { !it.reg.isNullOrEmpty() }
            .toStream().forEach { repository.save(it) }

        println("-- All Aircraft")
        repository.findAll().forEach { println(it) }
    }
}