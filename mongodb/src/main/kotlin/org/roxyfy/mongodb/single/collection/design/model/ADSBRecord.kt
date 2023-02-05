package org.roxyfy.mongodb.single.collection.design.model

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "aeroData")
@TypeAlias("ADSBRecord")
data class ADSBRecord(
    override val id: String,
    val altitude: Int?,
    val heading: Int?,
    val speed: Int?,
    val verticalSpeed: Int?,
    val timestamp: Date?,
    val geoPoint: GeoPoint?,
    override val recordType: Int = 3
) : AeroData()
