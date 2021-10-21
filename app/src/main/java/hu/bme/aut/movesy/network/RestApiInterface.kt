package hu.bme.aut.movesy.network

import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.model.Package
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RestApiInterface {

    //--------------------------------------
    //      AUTHENTICATION
    //--------------------------------------

    @POST("user/login")
    fun loginUser(
        @Body user: User,
    ): Response<ResponseBody>

    @POST("user/register")
    fun registerUser(
        @Body user: User,
    ): Response<ResponseBody>

    //--------------------------------------
    //      USER
    //--------------------------------------

    @GET("user/")
    fun getUser(
        @Query(value = "userId") userId: String,
    ): Response<User>

    @GET("user/list")
    fun getAllUsers(): Response<List<User>>

    @PUT("user/edit/")
    fun updateUser(
        @Query(value = "userId") userId: String,
        @Body user: User,
    ): Response<ResponseBody>

    @DELETE("user/delete/")
    fun deleteUser(
        @Query(value = "userId") userId: String,
    ): Response<ResponseBody>

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    @GET("package/list")
    fun getAllPackages(): Response<List<Package>>

    @GET("package/")
    fun getPackage(
        @Query(value = "packageId") packageId: String,
    ): Response<Package>

    @GET("package/user/userId")
    fun getPackagesOfUser(
        @Query(value = "userId") packageId: String,
    ): Response<List<Package>>

    @GET("package/transporter/transporterId")
    fun getPackagesOfTransporter(
        @Query(value= "transporterId") transformerId: String,
    ): Response<List<Package>>

    @POST("package/create/")
    fun createPackage(
        @Body newPackage: Package
    ): Response<ResponseBody>

    @PUT("package/edit/")
    fun updatePackage(
        @Query(value="packageId") packageId: String,
        @Body packageToEdit: Package
    ): Response<ResponseBody>

    @DELETE("package/delete/packageId")
    fun deletePackage(
        @Query(value = "packageId") packageId: String,
    ): Response<ResponseBody>

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    @GET("review/")
    fun getReviewOfPackage(
        @Query(value = "packageID") packageId: String,
    ): Response<Review>

    @GET("review/transporter/")
    fun getReviewsOfTransporter(
        @Query(value = "transporterID") transformerId: String
    ): Response<List<Review>>

    @POST("review/create")
    fun crateReview(
        @Body review: Review,
    ): Response<ResponseBody>

    @PUT("review/edit/")
    fun updateReview(
        @Query(value = "reviewID") reviewID: String,
        @Body review: Review
    ): Response<ResponseBody>

    @DELETE("review/delete/")
    fun deleteReview(
        @Query(value = "reviewID") reviewID: String
    ): Response<ResponseBody>


    //--------------------------------------
    //      OFFER
    //--------------------------------------

    @GET("offer/")
    fun getOffersOnPackage(
        @Query(value="packageId") packageId: String,
    ): Response<List<Offer>>

    @POST("offer/create/")
    fun createOffer(
        @Query(value = "packageId") packageId: String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @POST("offer/accept/")
    fun acceptOffer(
        @Query(value="packageId") packageId: String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @PUT("offer/edit/")
    fun updateOffer(
        @Query(value = "offerId") offerID:String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @DELETE("offer/delete/")
    fun deleteOffer(
        @Query(value = "offerId") offerID: String
    ): Response<ResponseBody>

}