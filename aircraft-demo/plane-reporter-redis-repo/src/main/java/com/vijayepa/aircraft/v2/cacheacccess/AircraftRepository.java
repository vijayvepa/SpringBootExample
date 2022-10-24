package com.vijayepa.aircraft.v2.cacheacccess;

import com.vijayepa.aircraft.v2.model.Aircraft;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface AircraftRepository extends CrudRepository<Aircraft, Long > {

}
