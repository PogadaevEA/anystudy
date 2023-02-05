package org.roxyfy.mongodb.single.collection.design.model

class GeoPoint(val type: String, val coordinates: Array<Double>) {
    constructor(coordinates: Array<Double>) : this("new", coordinates)
}
