//package org.roxyfy.mongodb.single.collection.design.service
//
//import jakarta.annotation.PostConstruct
//import org.roxyfy.mongodb.single.collection.design.model.ADSBRecord
//import org.roxyfy.mongodb.single.collection.design.model.Aircraft
//import org.roxyfy.mongodb.single.collection.design.model.Airline
//import org.roxyfy.mongodb.single.collection.design.model.GeoPoint
//import org.roxyfy.mongodb.single.collection.design.repository.ADSBRecordRepository
//import org.roxyfy.mongodb.single.collection.design.repository.AircraftRepository
//import org.roxyfy.mongodb.single.collection.design.repository.AirlineRepository
//import org.springframework.stereotype.Component
//import java.util.*
//
//@Component
//class AddDataOnStartService(
//    val airlineRepository: AirlineRepository,
//    val aircraftRepository: AircraftRepository,
//    val adsbRecordRepository: ADSBRecordRepository
//) {
//
//    @PostConstruct
//    fun run() {
//        airlineRepository.deleteAll()
//
//        // save an airline
//        airlineRepository.save(Airline("DAL", "Delta Air Lines", "United States", "US", "DELTA", "delta.com"))
//
//        // add some aircraft aircraft
//        aircraftRepository.save(Aircraft("DAL_a93d7c", "N695CA", "Bombardier Inc", "CL-600-2D24"))
//        aircraftRepository.save(Aircraft("DAL_ab8379", "N8409N", "Bombardier Inc", "CL-600-2B19"))
//        aircraftRepository.save(Aircraft("DAL_a36f7e", "N8409N", "Airbus Industrie", "A319-114"))
//
//        //Add some ADSB position reports
//        val coords1 = arrayOf(-4.776722, 55.991776)
//        var geoPoint: GeoPoint? = GeoPoint(coords1)
//        adsbRecordRepository.save(ADSBRecord("DAL_a36f7e_1", 38825, 319, 428, 1024, Date(1656980617041L), geoPoint))
//        val coords2 = arrayOf(-4.781466, 55.994843)
//        geoPoint = GeoPoint(coords2)
//        adsbRecordRepository.save(ADSBRecord("DAL_a36f7e_2", 38875, 319, 429, 1024, Date(1656980618041L), geoPoint))
//        val coords3 = arrayOf(-4.783344, 55.99606)
//        geoPoint = GeoPoint(coords3)
//        adsbRecordRepository.save(ADSBRecord("DAL_a36f7e_3", 38892, 319, 428, 1024, Date(1656980619041L), geoPoint))
//
//
//        // fetch all airlines
//        println("Airlines found with findAllAirlines():")
//        println("-------------------------------")
//        for (airline in airlineRepository.findAllAirlines()) {
//            println(airline)
//        }
//        println()
//        // fetch a specific airline by ICAO ID
//        println("Airlines found with findAirlineByIcaoAddr(\"DAL\"):")
//        println("-------------------------------")
//        val airlineResponse = airlineRepository.findAirlineByIcaoAddr("DAL")
//        println(airlineResponse)
//        println()
//
//        // fetch all aircraft
//        println("Aircraft found with findAllAircraft():")
//        println("-------------------------------")
//        for (aircraft in aircraftRepository.findAllAircraft()) {
//            println(aircraft)
//        }
//        println()
//        // fetch Aircraft Documents specific to airline "DAL"
//        println("Aircraft found with findAircraftDataByIcaoAddr(\"DAL\"):")
//        println("-------------------------------")
//        for (aircraft in aircraftRepository.findAircraftDataByIcaoAddr("DAL")) {
//            println(aircraft)
//        }
//        println()
//
//        // fetch Aircraft Documents specific to aircraft "a36f7e"
//        println("Aircraft found with findAircraftDataByIcaoAddr(\"DAL_a36f7e\"):")
//        println("-------------------------------")
//        for (aircraft in aircraftRepository.findAircraftDataByIcaoAddr("DAL_a36f7e")) {
//            println(aircraft)
//        }
//        println()
//
//        // fetch all adsb records
//        println("ADSB records found with findAllADSBRecords():")
//        println("-------------------------------")
//        for (adsb in adsbRecordRepository.findAllADSBRecords()) {
//            println(adsb)
//        }
//        println()
//        // fetch ADSB Documents specific to airline "DAL"
//        println("ADSB Documents found with findADSBDataByIcaoAddr(\"DAL\"):")
//        println("-------------------------------")
//        for (adsb in adsbRecordRepository.findADSBDataByIcaoAddr("DAL")) {
//            println(adsb)
//        }
//        println()
//
//        // fetch ADSB Documents specific to aircraft "a36f7e"
//        println("ADSB Documents found with findADSBDataByIcaoAddr(\"DAL_a36f7e\"):")
//        println("-------------------------------")
//        for (adsb in adsbRecordRepository.findADSBDataByIcaoAddr("DAL_a36f7e")) {
//            println(adsb)
//        }
//    }
//
//}
