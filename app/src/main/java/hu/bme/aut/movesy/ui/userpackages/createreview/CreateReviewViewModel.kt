package hu.bme.aut.movesy.ui.userpackages.createreview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.ReviewTransferObject
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject

@HiltViewModel
class CreateReviewViewModel @Inject constructor(
    private val userUtils: UserUtils,
    private val repository: Repository,
): ViewModel() {

    fun getRewiew(packageID: String) = repository.getReviewOfPackage(packageID)


    fun saveReview(review: ReviewTransferObject) =
        repository.createReview(review)

    fun updateReview(review: Review) =
        repository.editReview(review)

}