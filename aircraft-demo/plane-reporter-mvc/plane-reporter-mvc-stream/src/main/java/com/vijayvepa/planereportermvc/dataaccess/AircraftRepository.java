package com.vijayvepa.planereportermvc.dataaccess;

import com.vijayvepa.planereportermvc.model.Aircraft;
import org.springframework.data.repository.CrudRepository;


public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
