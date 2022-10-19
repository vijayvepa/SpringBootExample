package com.vijayvepa.restapidemo;

import com.vijayvepa.restapidemo.dataaccess.CoffeeRepository;
import com.vijayvepa.restapidemo.model.Coffee;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataLoader {
    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData(){
        this.coffeeRepository.saveAll(List.of(
                new Coffee("Café Cereza", "9a5393b1-9e2c-4204-9605-bf36e90f65ee"),
                new Coffee("Café Ganador", "4bb01dd0-b26b-44f6-ac7b-1d08ffb6c97b"),
                new Coffee("Café Lareño", "10cec660-08e6-40c9-b7f0-f6676238128d"),
                new Coffee("Café Três Pontas", "3c0f76f0-d3c3-45c5-99c2-c608f25c17c7")
        ));
    }
}
