package hu.bme.aut.movesy.ui.userpackages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.OfferInfo
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.viewmodel.Resource


class UserOfferViewModel (
    private val repository: Repository,
    private val packageId: String,
): ViewModel() {
    var offerInfos: List<OfferInfo>? = null
    var offers: LiveData<Resource<List<Offer>>> = liveData {  }

    init {
        refresh()
    }

    fun refresh(){
        offers = repository.getOffersOnPackage(packageId)
        offerInfos = offers.value?.data?.map {
            OfferInfo(
                transporterID = it.transporterID,
                price = it.price,
                transporterName = ""//transporterName = repository.getNameFromId(it.transporterID) ?: ""
            )
        }
    }
}