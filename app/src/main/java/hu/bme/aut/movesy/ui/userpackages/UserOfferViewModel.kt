package hu.bme.aut.movesy.ui.userpackages

import androidx.lifecycle.ViewModel
import hu.bme.aut.movesy.model.OfferInfo
import hu.bme.aut.movesy.repository.Repository


class UserOfferViewModel (
    private val repository: Repository,
    private val packageId: String,
): ViewModel() {
    var offerInfos: List<OfferInfo>? = null
    val offers = repository.getOffersOnPackage(packageId)

    init {
        refresh()
    }

    fun refresh(){
        offerInfos = repository.getOffersOnPackage(packageId).value?.data?.map {
            OfferInfo(
                transporterID = it.transporterID,
                price = it.price,
                transporterName = repository.getNameFromId(it.transporterID) ?: ""
            )
        }
    }
}