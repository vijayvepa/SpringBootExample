package com.vijayepa.aircraft.v2.cacheacccess;

import com.vijayepa.aircraft.v2.model.Aircraft;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

/**
 *  Cannot autogenerate id of type java.lang.Long for entity of type com.vijayepa.aircraft.v2.model.Aircraft!
 * @see <a href="https://stackoverflow.com/a/61693376/474377">StackOverflow</a>
 */
@Component
public interface AircraftRepository extends ReactiveCrudRepository<Aircraft, String > {

}
