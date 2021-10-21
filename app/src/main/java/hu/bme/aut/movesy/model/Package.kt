package hu.bme.aut.movesy.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "packages")
data class Package (
    @Json(name = "id") val id: String,
    var userID: String,
    var transporterID: String?,
    var from: String?,
    var to: String?,
    var deadline: String?,
    var price: Int?,
    var weight: Double?,
    var size: String?,
    var status: String?
)