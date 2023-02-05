package org.roxyfy.mongodb.single.collection.design.service

import org.roxyfy.mongodb.single.collection.design.dto.AirlineDto
import org.roxyfy.mongodb.single.collection.design.model.Airline

fun Airline.toDto() = AirlineDto(id, airlineName, country, countryISO, callsign, website)
