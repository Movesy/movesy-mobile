package hu.bme.aut.movesy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class UserTransferObject (
    @Json(name = "id") val id: String?,
    var username: String,
    var password: String,
    var email: String,
    var telephone: String?,
    var size: String?,
    var role: String?,
)