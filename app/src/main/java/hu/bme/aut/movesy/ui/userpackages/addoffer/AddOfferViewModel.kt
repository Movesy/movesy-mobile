package hu.bme.aut.movesy.ui.userpackages.addoffer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.OfferTransferObject
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject

@HiltViewModel
class AddOfferViewModel @Inject constructor(
    private val repository: Repository,
    private val userUtils: UserUtils
): ViewModel() {
    fun sendOffer(packageId: String, price: Int): LiveData<Resource<Offer>> {
        val offer = OfferTransferObject(
            null,
            userUtils.getUser()!!.id,
            packageId,
            price
        )
        return repository.createOffer(offer)
    }

}
