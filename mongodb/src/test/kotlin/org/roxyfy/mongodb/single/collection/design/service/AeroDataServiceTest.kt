package org.roxyfy.mongodb.single.collection.design.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.roxyfy.mongodb.single.collection.design.repository.AirlineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AeroDataServiceTest {

    companion object {
        @Container
        val mongoDbContainer: MongoDBContainer = MongoDBContainer("mongo:6.0.3")

        @DynamicPropertySource
        fun setUpProp(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl)
        }
    }

    @Autowired
    lateinit var airlineRepository: AirlineRepository

    @Test
    fun should_return_all_airlines_happy_path() {
//        airlineRepository.save(Airline("DAL", "Delta Air Lines", "United States", "US", "DELTA", "delta.com"))
        val result = airlineRepository.findAllAirlines()

        assertThat(result).hasSize(1)
    }
}
