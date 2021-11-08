package hu.bme.aut.movesy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "reviews")
class Review (
    @PrimaryKey
    @Json(name = "id") val id: String,
    var transporterID: String,
    var packageID: String,
    var time: String,
    var rating: Double,
    var description: String,
    var customerUsername: String,
        )