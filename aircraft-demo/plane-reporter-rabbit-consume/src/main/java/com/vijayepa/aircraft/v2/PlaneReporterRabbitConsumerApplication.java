package com.vijayepa.aircraft.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class PlaneReporterRabbitConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaneReporterRabbitConsumerApplication.class, args);
    }

    /**
     * @implNote See https://stackoverflow.com/a/66405182/474377
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
