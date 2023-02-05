package org.roxyfy.mongodb.single.collection.design.controllers

import org.roxyfy.mongodb.single.collection.design.dto.AirlineDto
import org.roxyfy.mongodb.single.collection.design.service.AeroDataService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aero-data")
class AeroDataController(val aeroDataService: AeroDataService) {

    @GetMapping("/airline/all")
    fun getAllAirlines() : List<AirlineDto> {
        return aeroDataService.getAllAirlines();
    }
}
