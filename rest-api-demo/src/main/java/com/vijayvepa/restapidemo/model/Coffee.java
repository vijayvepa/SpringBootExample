package com.vijayvepa.restapidemo.model;

import java.util.UUID;

public class Coffee {
    private final String id;
    private String name;

    public Coffee(
            String name,
            String id) {
        this.id = id;
        this.name = name;
    }

    public Coffee(String name){
        this(name, UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
