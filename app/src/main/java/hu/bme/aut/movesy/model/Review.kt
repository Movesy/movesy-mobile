package hu.bme.aut.movesy.model

import com.squareup.moshi.Json

class Review (
    @Json(name = "_id") val id: String,
    var transporterID: String,
    var packageID: String,
    var time: String,
    var rating: Double,
    var description: String,
    var customerUsername: String
        )