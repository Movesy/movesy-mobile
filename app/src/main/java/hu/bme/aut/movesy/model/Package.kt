package hu.bme.aut.movesy.model

import com.squareup.moshi.Json

data class Package (
    @Json(name = "_id") val id: String,
    var userID: String,
    var transporterID: String,
    var from: String,
    var to: String,
    var deadline: String,
    var price: Int,
    var weight: Double,
    var size: String,
    var status: String
)