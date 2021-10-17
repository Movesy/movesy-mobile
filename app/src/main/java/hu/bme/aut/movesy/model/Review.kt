package hu.bme.aut.movesy.model

import com.squareup.moshi.Json

class Review (
    @Json(name = "id") val id: String,
    var transporterID: String,
    var packageID: String,
    var time: String,
    var rating: Double,
    var description: String,
    var customerUsername: String
        )