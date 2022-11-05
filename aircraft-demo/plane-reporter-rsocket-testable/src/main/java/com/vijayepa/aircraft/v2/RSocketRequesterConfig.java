package com.vijayepa.aircraft.v2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
public class RSocketRequesterConfig {
    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder){
        return builder.tcp("localhost", 7635);
    }
}
