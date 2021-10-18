package hu.bme.aut.movesy.network

import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.model.Package
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RestApiInterface {

    //--------------------------------------
    //      AUTHENTICATION
    //--------------------------------------

    @POST("user/login")
    fun loginUser(
        @Body user: User,
    ): Call<ResponseBody>

    @POST("user/register")
    fun registerUser(
        @Body user: User,
    ): Call<ResponseBody>

    //--------------------------------------
    //      USER
    //--------------------------------------

    @GET("user/")
    fun getUser(
        @Query(value = "userId") userId: String,
    ): Call<User>

    @GET("user/list")
    fun getAllUsers(): Call<List<User>>

    @PUT("user/edit/")
    fun updateUser(
        @Query(value = "userId") userId: String,
        @Body user: User,
    ): Call<ResponseBody>

    @DELETE("user/delete/")
    fun deleteUser(
        @Query(value = "userId") userId: String,
    ): Call<ResponseBody>

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    @GET("package/list")
    fun getAllPackages(): Call<List<Package>>

    @GET("package/")
    fun getPackage(
        @Query(value = "packageId") packageId: String,
    ): Call<Package>

    @GET("package/user/userId")
    fun getPackagesOfUser(
        @Query(value = "userId") packageId: String,
    ): Call<List<Package>>

    @GET("package/transporter/transporterId")
    fun getPackagesOfTransporter(
        @Query(value= "transporterId") transformerId: String,
    ): Call<List<Package>>

    @POST("package/create/")
    fun createPackage(
        @Body newPackage: Package
    ): Call<ResponseBody>

    @PUT("package/edit/")
    fun editPackage(
        @Query(value="packageId") packageId: String,
        @Body packageToEdit: Package
    ): Call<ResponseBody>

    @DELETE("package/delete/packageId")
    fun deletePackage(
        @Query(value = "packageId") packageId: String,
    ): Call<ResponseBody>

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    @GET("review/")
    fun getReviewOfPackage(
        @Query(value = "packageID") packageId: String,
    ): Call<Review>

    @GET("review/transporter/")
    fun getReviewsOfTransporter(
        @Query(value = "transporterID") transformerId: String
    ): Call<List<Review>>

    @POST("review/create")
    fun crateReview(
        @Body review: Review,
    ): Call<ResponseBody>

    @PUT("review/edit/")
    fun updateReview(
        @Query(value = "reviewID") reviewID: String,
        @Body review: Review
    ): Call<ResponseBody>

    @DELETE("review/delete/")
    fun deleteReview(
        @Query(value = "reviewID") reviewID: String
    ): Call<ResponseBody>


    //--------------------------------------
    //      OFFER
    //--------------------------------------

    @GET("offer/")
    fun getOffersOnPackage(
        @Query(value="packageId") packageId: String,
    ): Call<List<Offer>>

    @POST("offer/create/")
    fun createOffer(
        @Query(value = "packageId") packageId: String,
        @Body offer: Offer
    ): Call<ResponseBody>

    @POST("offer/accept/")
    fun acceptOffer(
        @Query(value="packageId") packageId: String,
        @Body offer: Offer
    ): Call<ResponseBody>

    @PUT("offer/edit/")
    fun editOffer(
        @Query(value = "offerId") offerID:String,
        @Body offer: Offer
    ): Call<ResponseBody>

    @DELETE("offer/delete/")
    fun deleteOffer(
        @Query(value = "offerId") offerID: String
    ):Call<ResponseBody>

}