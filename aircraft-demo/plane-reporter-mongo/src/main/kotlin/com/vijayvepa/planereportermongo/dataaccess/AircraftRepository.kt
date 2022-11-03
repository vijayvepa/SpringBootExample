package com.vijayvepa.planereportermongo.dataaccess

import com.vijayvepa.planereportermongo.model.Aircraft
import org.springframework.data.repository.CrudRepository

interface AircraftRepository : CrudRepository<Aircraft, String>