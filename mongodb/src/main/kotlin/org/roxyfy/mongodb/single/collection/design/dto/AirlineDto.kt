package org.roxyfy.mongodb.single.collection.design.dto

data class AirlineDto(
    val id: String,
    val airlineName: String?,
    val country: String?,
    val countryISO: String?,
    val callsign: String?,
    val website: String?,
)
