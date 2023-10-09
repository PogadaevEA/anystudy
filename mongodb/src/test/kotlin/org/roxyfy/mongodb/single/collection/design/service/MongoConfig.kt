//package org.roxyfy.mongodb.single.collection.design.service
//
//import org.springframework.boot.test.context.TestConfiguration
//import org.springframework.test.context.DynamicPropertyRegistry
//import org.springframework.test.context.DynamicPropertySource
//import org.testcontainers.containers.MongoDBContainer
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//import org.testcontainers.utility.DockerImageName
//
//@TestConfiguration
//@Testcontainers
//class MongoConfig {
//
//    @Container
//    var container = MongoDBContainer(DockerImageName.parse("mongo:5"))
//
//    @DynamicPropertySource
//    fun mongoDbProperties(registry: DynamicPropertyRegistry) {
//        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl)
//    }
//}
