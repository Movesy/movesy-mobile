package hu.bme.aut.movesy.network

import android.util.Log
import hu.bme.aut.movesy.model.*
import hu.bme.aut.movesy.viewmodel.Resource
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestAPI @Inject constructor(
    private val restApiInterface: RestApiInterface
    ){

    companion object{
        const val BASE_URL = "https://movesy.herokuapp.com/"
    }

    //--------------------------------------
    //      AUTHENTICATION
    //--------------------------------------

    suspend fun loginUser(user: User): Resource<Token> {
        try {
            Log.d("debug", "asdf")
            val response = restApiInterface.loginUser(user)
            Log.d("debug", "asdf")
            Log.d("debug", "asdf ${response.toString()}")
            if (response.isSuccessful) {
                Log.d("debug", response.body().toString())
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun registerUser(user: User) = getResult { restApiInterface.registerUser(user) }

    //--------------------------------------
    //      USER
    //--------------------------------------

    suspend fun getUser(userID: String) = getResult { restApiInterface.getUser(userID) }

    suspend fun getAllUser() = getResult { restApiInterface.getAllUsers() }

    ///TODO REDUNDÁNS PARAMÉTER FIX
    suspend fun updateUser(user: User) = getResult { restApiInterface.updateUser(user.id, user) }

    suspend fun deleteUser(userID: String) = getResult { restApiInterface.deleteUser(userID) }

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    suspend fun getAllPackages() = getResult { restApiInterface.getAllPackages() }

    suspend fun getPackage(packageID: String) = getResult { restApiInterface.getPackage(packageID) }

    suspend fun getPackagesOfOwner(userID: String) = getResult { restApiInterface.getPackagesOfUser(userID) }

    suspend fun getPackageOfTransporter(transporterID: String) = getResult { restApiInterface.getPackagesOfTransporter(transporterID) }

    suspend fun createPackage(newPackage: Package) = getResult { restApiInterface.createPackage(newPackage) }

    suspend fun updatePackage(packageToEdit: Package) = getResult { restApiInterface.updatePackage(packageToEdit.id, packageToEdit) }

    suspend fun deletePackage(packageID: String) = getResult { restApiInterface.deletePackage(packageID) }

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    suspend fun getReviewOfPackage(packageID: String) = getResult { restApiInterface.getReviewOfPackage(packageID) }

    suspend fun getReviewOfTransporter(transporterID: String) = getResult { restApiInterface.getReviewsOfTransporter(transporterID) }

    suspend fun createReview(review: Review) = getResult { restApiInterface.crateReview(review) }

    suspend fun updateReview(review: Review) = getResult { restApiInterface.updateReview(review.id, review) }

    suspend fun deleteReview(reviewID : String) = getResult { restApiInterface.deleteReview(reviewID) }

    //--------------------------------------
    //      OFFER
    //--------------------------------------

    suspend fun getOffersOnPackage(packageID: String) = getResult { restApiInterface.getOffersOnPackage(packageID) }

    suspend fun createOffer(offer: Offer) = getResult { restApiInterface.createOffer(offer.packageID, offer) }

    suspend fun acceptOffer(offerID: String) = getResult { restApiInterface.acceptOffer(offerID) }

    suspend fun updateOffer(offer: Offer) = getResult { restApiInterface.updateOffer(offer.id, offer) }

    suspend fun deleteOffer(offerID: String) = getResult { restApiInterface.deleteOffer(offerID) }

    //----------------------------
    //      HELPER FUNCTIONS
    //----------------------------

    private suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("error", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }


}