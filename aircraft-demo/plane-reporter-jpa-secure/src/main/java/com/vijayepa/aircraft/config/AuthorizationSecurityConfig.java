package com.vijayepa.aircraft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class AuthorizationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/aircraft-admin/**").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .formLogin().and()
                .httpBasic();
    }

    @Bean
    UserDetailsService authentication() {
        UserDetails peter = User.builder().username("peter")
                .password(passwordEncoder.encode("ppassword"))
                .roles("USER")
                .build();

        UserDetails jodie = User.builder().username("jodie")
                .password(passwordEncoder.encode("jpassword"))
                .roles("USER", "ADMIN")
                .build();

        System.out.println("   >>> Peter's Password: " + peter.getPassword());
        System.out.println("   >>> Jodie's Password: " + jodie.getPassword());

        return new InMemoryUserDetailsManager(peter, jodie);
    }
}
