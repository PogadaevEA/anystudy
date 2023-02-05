package org.roxyfy.mongodb.single.collection.design.repository

import org.roxyfy.mongodb.single.collection.design.model.Airline
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface AirlineRepository :  MongoRepository<Airline, String> {

    @Query("{_class: \"AirlineData\"}")
    fun findAllAirlines() : List<Airline>

    @Query(value="{_id: /^?0/, _class: \"AirlineData\"}", sort = "{_id: 1}")
    fun findAirlineByIcaoAddr(icaoAddr: String) : Airline?
}


