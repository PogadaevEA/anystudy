package org.roxyfy.mongodb.single.collection.design.service

import org.roxyfy.mongodb.single.collection.design.dto.AirlineDto
import org.roxyfy.mongodb.single.collection.design.repository.ADSBRecordRepository
import org.roxyfy.mongodb.single.collection.design.repository.AircraftRepository
import org.roxyfy.mongodb.single.collection.design.repository.AirlineRepository
import org.springframework.stereotype.Service

@Service
class AeroDataService(
    val airlineRepository: AirlineRepository,
    val aircraftRepository: AircraftRepository,
    val adsbRecordRepository: ADSBRecordRepository,
) {

    fun getAllAirlines() : List<AirlineDto> {
        return airlineRepository.findAllAirlines()
            .map {  it.toDto() }
            .toList()
    }
}
