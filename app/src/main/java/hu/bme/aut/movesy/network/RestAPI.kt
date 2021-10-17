package hu.bme.aut.movesy.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.Package
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI {

    private val restApiInterface: RestApiInterface

    companion object{
        ///TODO replace with proper url
        const val BASE_URL = "https://movesy.herokuapp.com/"
    }

    init{
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        restApiInterface = retrofit.create(RestApiInterface::class.java)
    }

    //--------------------------------------
    //      AUTHENTICATION
    //--------------------------------------

    fun loginUser(user: User, handler: ResponseHandler<ResponseBody>){
        restApiInterface.loginUser(user).enqueue(handler)
    }

    fun registerUser(user: User, handler: ResponseHandler<ResponseBody>){
        restApiInterface.registerUser(user).enqueue(handler)
    }

    //--------------------------------------
    //      USER
    //--------------------------------------

    fun getUser(userID: String, handler: ResponseHandler<User>){
        restApiInterface.getUser(userID).enqueue(handler)
    }

    fun getAllUser(handler: ResponseHandler<List<User>>){
        restApiInterface.getAllUsers().enqueue(handler)
    }

    fun editUser(user: User, handler:ResponseHandler<ResponseBody>){
        ///TODO REDUNDÁNS PARAMÉTER FIX
        restApiInterface.updateUser(user.id, user).enqueue(handler)
    }

    fun deleteUser(userID: String, handler:ResponseHandler<ResponseBody>){
        restApiInterface.deleteUser(userID).enqueue(handler)
    }

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    fun getAllPackages(handler: ResponseHandler<List<Package>>){
        restApiInterface.getAllPackages().enqueue(handler)
    }

    fun getPackage(packageID: String, handler: ResponseHandler<Package>){
        restApiInterface.getPackage(packageID).enqueue(handler)
    }

    fun getPackagesOfOwner(userID: String, handler: ResponseHandler<List<Package>>){
        restApiInterface.getPackagesOfUser(userID).enqueue(handler)
    }

    fun getPackageOfTransporter(transporterID: String, handler: ResponseHandler<List<Package>>){
        restApiInterface.getPackagesOfTransporter(transporterID).enqueue(handler)
    }

    fun createPackage(newPackage: Package, handler: ResponseHandler<ResponseBody>){
        restApiInterface.createPackage(newPackage).enqueue(handler)
    }

    fun editPackage(packageToEdit: Package, handler: ResponseHandler<ResponseBody>){
        restApiInterface.editPackage(packageToEdit.id, packageToEdit).enqueue(handler)
    }

    fun deletePackage(packageID: String, handler: ResponseHandler<ResponseBody>){
        restApiInterface.deletePackage(packageID).enqueue(handler)
    }

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    fun getReviewOfPackage(packageID: String, handler: ResponseHandler<Review>){
        restApiInterface.getReviewOfPackage(packageID).enqueue(handler)
    }

    fun getReviewOfTransporter(transporterID: String, handler: ResponseHandler<List<Review>>){
        restApiInterface.getReviewsOfTransporter(transporterID).enqueue(handler)
    }

    fun createReview(review: Review, handler: ResponseHandler<ResponseBody>){
        restApiInterface.crateReview(review).enqueue(handler)
    }

    fun editReview(review: Review, handler: ResponseHandler<ResponseBody>){
        restApiInterface.updateReview(review.id, review).enqueue(handler)
    }

    fun deleteReview(reviewID : String, handler: ResponseHandler<ResponseBody>){
        restApiInterface.deleteReview(reviewID).enqueue(handler)
    }

    //--------------------------------------
    //      OFFER
    //--------------------------------------

    fun getOffersOnPackage(packageID: String, handler: ResponseHandler<List<Offer>>){
        restApiInterface.getOffersOnPackage(packageID).enqueue(handler)
    }

    fun createOffer(offer: Offer, handler: ResponseHandler<ResponseBody>){
        restApiInterface.createOffer(offer.packageID, offer).enqueue(handler)
    }

    fun acceptOffer(offer: Offer, handler: ResponseHandler<ResponseBody>){
        restApiInterface.acceptOffer(offer.packageID, offer).enqueue(handler)
    }

    fun editOffer(offer: Offer, handler: ResponseHandler<ResponseBody>){
        restApiInterface.editOffer(offer.id, offer).enqueue(handler)
    }

    fun deleteOffer(offerID: String, handler: ResponseHandler<ResponseBody>){
        restApiInterface.deleteOffer(offerID).enqueue(handler)
    }
}