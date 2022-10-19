package com.vijayvepa.restapidemo.dataaccess;

import com.vijayvepa.restapidemo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, String> {
}
