package org.roxyfy.mongodb.single.collection.design.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "aeroData")
@TypeAlias("AirlineData")
data class Airline(
    override val id: String,
    val airlineName: String?,
    val country: String?,
    val countryISO: String?,
    val callsign: String?,
    val website: String?,
    override val recordType: Int = 1,
) : AeroData()
