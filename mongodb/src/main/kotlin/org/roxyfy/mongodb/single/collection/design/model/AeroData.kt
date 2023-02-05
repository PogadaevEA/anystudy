package org.roxyfy.mongodb.single.collection.design.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "aeroData")
abstract class AeroData {
    @get:Id
    abstract val id: String
    abstract val recordType: Int

}
