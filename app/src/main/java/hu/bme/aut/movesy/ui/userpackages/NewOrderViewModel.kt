package hu.bme.aut.movesy.ui.userpackages

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Location
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.network.Geocoder
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.viewmodel.Resource
import okhttp3.ResponseBody
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun  addNewOrder(pack: Package): LiveData<Resource<ResponseBody>>{
        return repository.createPackage(pack)
    }

    fun geocodeFromAndTo(context: Context,from: String, to:String) =
            object : MediatorLiveData<Pair<Location?, Location?>>() {
                var _from: Location? = null
                var _to: Location? = null

                init {
                    addSource(Geocoder(context, from).geocode()) { location->
                        this._from = location.data
                        _to?.let { value = this._from to it }
                    }
                    addSource(Geocoder(context, to).geocode()) { location ->
                        this._to = location.data
                        _from?.let { value = it to _to }
                    }
                }
            }

}