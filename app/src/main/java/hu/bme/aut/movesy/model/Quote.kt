package hu.bme.aut.movesy.model

import com.squareup.moshi.Json

class Quote (
    @Json(name = "_id") val id: String,
    var transporterID: String,
    var packageID: String,
    var price: Int
    )