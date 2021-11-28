package hu.bme.aut.movesy.ui.userpackages.offers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.OfferInfo
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.Status
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class UserOfferViewModel @Inject constructor(
    private val repository: Repository,
): ViewModel() {

    fun getOffers(packageId: String): LiveData<Resource<List<OfferInfo>>> =
        object : MediatorLiveData<Resource<List<OfferInfo>>>() {
            var _users: List<User>? = null
            var _offers: List<Offer>? = null

            init {
                addSource(repository.getAllUser()) { result ->
                    this._users = result.data
                    when(result.status){
                        Status.SUCCESS -> _offers?.let { value = Resource.success(mapUsersToOffers(_users!!, _offers!!)) }
                        Status.LOADING -> value = Resource.loading(emptyList())
                        Status.ERROR -> value = Resource.error(result.message ?: "An unknown error has occurred")
                    }
                }
                addSource(repository.getOffersOnPackage(packageId)) { result ->
                    this._offers = result.data
                    when(result.status){
                        Status.SUCCESS -> _users?.let { value = Resource.success(mapUsersToOffers(_users!!, _offers!!)) }
                        Status.LOADING -> value = Resource.loading(emptyList())
                        Status.ERROR -> value = Resource.error(result.message ?: "An unknown error has occurred")
                    }
                }
            }
        }

    fun mapUsersToOffers(users: List<User>, offers: List<Offer>): List<OfferInfo>{
        return offers.map { offer ->
            val transporter = users.find { user -> user.id == offer.transporterID }?.username ?: return emptyList()
            OfferInfo(
                id = offer.id,
                transporterID = offer.transporterID,
                packageID = offer.packageID,
                price = offer.price,
                transporterName = transporter
            )
        }
    }

    fun deleteOffer(offerID: String): LiveData<Resource<ResponseBody>> {
        Log.d("debug", "deleting offer: ${offerID}")
        return repository.deleteOffer(offerID)
    }

    fun acceptOffer(offer: Offer): LiveData<Resource<ResponseBody>> {
        Log.d("debug", "accepting offer: ${offer.id}")
        return repository.acceptOffer(offer)
    }

}