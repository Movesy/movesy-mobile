package hu.bme.aut.movesy.network

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.liveData
import hu.bme.aut.movesy.model.Location
import hu.bme.aut.movesy.viewmodel.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class Geocoder(private val context: Context, private val location: String) {

    fun geocode() = liveData<Resource<Location>>(Dispatchers.IO) {
            try {
                emit(Resource.loading(Location(0.0,0.0)))
                val geocoder = Geocoder(context, Locale.getDefault())
                val address: Address  = geocoder.getFromLocationName(
                    location,
                    1
                ).firstOrNull() ?: throw RuntimeException("No address found")
                if (address.hasLatitude() && address.hasLongitude()){
                    emit(Resource.success(Location(address.latitude, address.longitude)))
                } else {
                    emit(Resource.error("No address found"))
                }
            } catch (e: Exception) {
                Log.d("debug", "error: $e")
                emit(Resource.error("No address found"))
            }
    }
}