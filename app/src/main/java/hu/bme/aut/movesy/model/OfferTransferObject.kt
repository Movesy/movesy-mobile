package hu.bme.aut.movesy.model

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class OfferTransferObject (
    @Json(name = "id") val id: String?,
    var transporterID: String,
    var packageID: String,
    var price: Int,
)