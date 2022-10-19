package com.vijayvepa.restapidemo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private final String name;
    private final String second;

    private final String third;

    public GreetingController(
            @Value("${greeting-name: Mirage}") String name,
            @Value("${second-greeting: Mala Kaliki Maka}") String secondGreeting,
            @Value("${greeting-coffee: ${greeting-name} is drinking Coffee}") String third) {
        this.name = name;
        this.second = secondGreeting;
        this.third = third;
    }

    @GetMapping
    String getGreeting() {
        return name;
    }

    @GetMapping("/second")
    String getSecondGreeting() {
        return second;
    }

    @GetMapping("/coffee")
    String getCoffee() {
        return third;
    }
}
