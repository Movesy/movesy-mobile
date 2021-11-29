package hu.bme.aut.movesy.ui.userpackages.neworder

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Location
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.model.PackageTransferObject
import hu.bme.aut.movesy.network.Geocoder
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.Status
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun addNewOrder(pack: PackageTransferObject): LiveData<Resource<Package>> {
        return repository.createPackage(pack)
    }

    fun editPackage(pack:Package) = repository.updatePackage(pack)

    fun geocodeFromAndTo(context: Context, from: String, to: String) =
        object : MediatorLiveData<Resource<Pair<Location?, Location?>>>() {
            var _from: Location? = null
            var _to: Location? = null

            init {
                addSource(Geocoder(context, from).geocode()) { location ->
                    when (location.status) {
                        Status.SUCCESS -> {
                            this._from = location.data
                            _to?.let { value = Resource.success(this._from to it) }
                        }

                        Status.ERROR -> {
                            value = Resource.error("Given adress not found!", null)
                        }
                    }
                }
                addSource(Geocoder(context, to).geocode()) { location ->

                    when (location.status) {
                        Status.SUCCESS -> {
                            this._to = location.data
                            _from?.let { value = Resource.success(this._to to it) }

                        }

                        Status.ERROR -> {
                            value = Resource.error("Given adress not found!", null)
                        }
                    }
                }
            }
        }
}