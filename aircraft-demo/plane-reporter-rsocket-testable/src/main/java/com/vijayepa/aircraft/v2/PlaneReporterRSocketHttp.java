package com.vijayepa.aircraft.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class PlaneReporterRSocketHttp {

    public static void main(String[] args) {
        //Hooks.onOperatorDebug();
        SpringApplication.run(PlaneReporterRSocketHttp.class, args);
    }

}
