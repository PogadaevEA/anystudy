package org.roxyfy.mongodb.single.collection.design.repository

import org.roxyfy.mongodb.single.collection.design.model.Aircraft
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface AircraftRepository : MongoRepository<Aircraft, String> {

    @Query("{_class: \"AircraftData\"}")
    fun findAllAircraft(): List<Aircraft>

    @Query("{_id:  /^?0/, _class: \"AircraftData\"}")
    fun findAircraftDataByIcaoAddr(icaoAddr: String): List<Aircraft>
}
