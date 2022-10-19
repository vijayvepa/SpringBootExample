package com.vijayvepa.restapidemo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Coffee {
    @Id
    private String id;
    private String name;

    public Coffee() {

    }

    public Coffee(
            String name,
            String id) {
        this.id = id;
        this.name = name;
    }

    public Coffee(String name) {
        this(name, UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
