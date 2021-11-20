package hu.bme.aut.movesy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "packages")
data class Package (
    @PrimaryKey
    @Json(name = "id") val id: String,
    var userID: String,
    val name: String?,
    var transporterID: String?,
    var from: Location?,
    var to: Location?,
    var deadline: String?,
    var price: Int?,
    var weight: Double?,
    var size: String?,
    var status: String?,
    var creationDate: String?,
)