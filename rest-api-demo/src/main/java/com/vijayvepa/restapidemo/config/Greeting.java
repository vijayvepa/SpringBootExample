package com.vijayvepa.restapidemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "greeting")
public class Greeting {
    private String name;
    private String coffee;

    public Greeting(){
        name = "Mirage";
        coffee = name + " is drinking coffee";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String coffee) {
        this.coffee = coffee;
    }
}
