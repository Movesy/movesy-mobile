package hu.bme.aut.movesy.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "users")
data class User (
    @Json(name = "id") val id: String,
    var username: String,
    var password: String,
    var email: String,
    var telephone: String,
    var size: String?,
    var role: String?
)