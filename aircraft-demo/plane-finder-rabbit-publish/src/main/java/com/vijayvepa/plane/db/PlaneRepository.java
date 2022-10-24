package com.vijayvepa.plane.db;

import com.vijayvepa.plane.model.Aircraft;
import org.springframework.data.repository.CrudRepository;

public interface PlaneRepository extends CrudRepository<Aircraft, Long> {
}
