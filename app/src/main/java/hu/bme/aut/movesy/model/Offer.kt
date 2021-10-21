package hu.bme.aut.movesy.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "offers")
class Offer (
    @Json(name = "id") val id: String,
    var transporterID: String,
    var packageID: String,
    var price: Int
    )