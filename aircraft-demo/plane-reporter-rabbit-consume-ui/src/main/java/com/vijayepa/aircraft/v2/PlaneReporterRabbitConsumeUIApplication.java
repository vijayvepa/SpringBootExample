package com.vijayepa.aircraft.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class PlaneReporterRabbitConsumeUIApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaneReporterRabbitConsumeUIApplication.class, args);
    }

    /**
     * @implNote See <a href="https://stackoverflow.com/a/66405182/474377">JavaTimeModule Issue</a>
     * @param builder builder
     * @return mapper
     */

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
