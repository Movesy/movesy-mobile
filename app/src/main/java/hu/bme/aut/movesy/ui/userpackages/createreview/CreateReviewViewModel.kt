package hu.bme.aut.movesy.ui.userpackages.createreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.viewmodel.UserUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReviewViewModel @Inject constructor(
    private val userUtils: UserUtils,
    private val repository: Repository,
): ViewModel() {

    fun getRewiew(packageID: String) = repository.getReviewOfPackage(packageID)

    fun getTransporterName(transporterId: String) =
        repository.getAllUser()

    fun saveReview(review: Review) =
        repository.createReview(review)

    fun updateReview(review: Review) =
        repository.editReview(review)

}