package org.roxyfy.mongodb.single.collection.design.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roxyfy.mongodb.single.collection.design.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataMongoTest
public class AeroTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.3");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private AirlineRepository airlineRepository;

    @AfterEach
    void cleanUp() {
        this.airlineRepository.deleteAll();
    }

    @Test
    void test() {
        airlineRepository.findAllAirlines();
    }
}
