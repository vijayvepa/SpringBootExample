package com.vijayvepa.restapidemo.controllers;

import com.vijayvepa.restapidemo.dataaccess.CoffeeRepository;
import com.vijayvepa.restapidemo.model.Coffee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
public class CoffeeController {
    private final CoffeeRepository coffeeRepository;

    public CoffeeController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;

        this.coffeeRepository.saveAll(List.of(
                new Coffee("Café Cereza", "9a5393b1-9e2c-4204-9605-bf36e90f65ee"),
                new Coffee("Café Ganador", "4bb01dd0-b26b-44f6-ac7b-1d08ffb6c97b"),
                new Coffee("Café Lareño", "10cec660-08e6-40c9-b7f0-f6676238128d"),
                new Coffee("Café Três Pontas", "3c0f76f0-d3c3-45c5-99c2-c608f25c17c7")
        ));
    }

    @GetMapping(value = "/coffees")
    Iterable<Coffee> getCoffees() {
        return coffeeRepository.findAll();
    }

    @GetMapping(value = "/coffees/{id}")
    Optional<Coffee> getCoffee(@PathVariable String id) {
        return coffeeRepository.findById(id);
    }

    @PostMapping("/coffees")
    Coffee postCoffee(@RequestBody Coffee coffee) {
      return   coffeeRepository.save(coffee);
    }

    @PutMapping("/coffees/{id}")
    ResponseEntity<Coffee> putCoffee(
            @PathVariable String id,
            @RequestBody Coffee coffee) {

        if (!Objects.equals(id, coffee.getId())) {
            return new ResponseEntity<>(coffee, HttpStatus.BAD_REQUEST);
        }

        final boolean exists = coffeeRepository.existsById(id);

        final Coffee savedCoffee = coffeeRepository.save(coffee);


        return exists ? new ResponseEntity<>(savedCoffee, HttpStatus.OK) : new ResponseEntity<>(savedCoffee, HttpStatus.CREATED);
    }

    @DeleteMapping("/coffees/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffeeRepository.deleteById(id);
    }

}
