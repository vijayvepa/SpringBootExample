package com.vijayvepa.plane;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class OAuthSecurityConfig {

    //@Bean
    ReactiveJwtDecoder reactiveJwtDecoder(){
        return new NimbusReactiveJwtDecoder("");
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.authorizeExchange()
                .pathMatchers("/aircraft/**").hasAuthority("SCOPE_closedid")
                .pathMatchers("/aircraft-admin/**").hasAuthority("SCOPE_openid")
                .and().oauth2ResourceServer().jwt();
        return http.build();

    }
}
