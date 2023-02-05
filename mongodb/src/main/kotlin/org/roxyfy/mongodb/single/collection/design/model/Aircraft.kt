package org.roxyfy.mongodb.single.collection.design.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "aeroData")
@TypeAlias("AircraftData")
data class Aircraft(
    override val id: String,
    val tailNumber: String?,
    val manufacturer: String?,
    val model: String?,
    override val recordType: Int = 2,
) : AeroData()
