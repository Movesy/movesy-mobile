package hu.bme.aut.movesy.database

import androidx.room.TypeConverter
import hu.bme.aut.movesy.model.Location

class Converter {

    @TypeConverter
    fun fromLocationToString(value: Location?): String? {
        return value?.let{ "${value.latitude};${value.longitude}"}
    }

    @TypeConverter
    fun fromStringToLocation(value: String?): Location? {
        return value?.let {
            val splitted = it.split(";")
            Location(splitted[0].toDouble(),splitted[1].toDouble())
        }
    }
}