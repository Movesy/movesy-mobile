package hu.bme.aut.movesy.ui.userpackages

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.repository.Repository
import javax.inject.Inject

@HiltViewModel
class TransporterCommentsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun getCommentsOnTransporter(transporterID:String ) = repository.getReviewOfTransporter(transporterID)
}