package hu.bme.aut.movesy.network

import hu.bme.aut.movesy.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RestApiInterface {

    //--------------------------------------
    //      AUTHENTICATION
    //--------------------------------------

    @POST("authenticate")
    suspend fun loginUser(
        @Body user: User,
    ): Response<Token>

    @POST("register")
    suspend fun registerUser(
        @Body user: User,
    ): Response<ResponseBody>

    //--------------------------------------
    //      USER
    //--------------------------------------

    @GET("user/")
    suspend fun getUser(
        @Query(value = "userId") userId: String,
    ): Response<User>

    @GET("user/list")
    suspend fun getAllUsers(): Response<List<User>>

    @PUT("user/edit/")
    suspend fun updateUser(
        @Query(value = "userId") userId: String,
        @Body user: User,
    ): Response<ResponseBody>

    @DELETE("user/delete/")
    suspend fun deleteUser(
        @Query(value = "userId") userId: String,
    ): Response<ResponseBody>

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    @GET("package/list")
    suspend fun getAllPackages(): Response<List<Package>>

    @GET("package/")
    suspend fun getPackage(
        @Query(value = "packageId") packageId: String,
    ): Response<Package>

    @GET("package/user/userId")
    suspend fun getPackagesOfUser(
        @Query(value = "userId") packageId: String,
    ): Response<List<Package>>

    @GET("package/transporter/transporterId")
    suspend fun getPackagesOfTransporter(
        @Query(value= "transporterId") transformerId: String,
    ): Response<List<Package>>

    @POST("package/create/")
    suspend fun createPackage(
        @Body newPackage: Package
    ): Response<ResponseBody>

    @PUT("package/edit/")
    suspend fun updatePackage(
        @Query(value="packageId") packageId: String,
        @Body packageToEdit: Package
    ): Response<ResponseBody>

    @DELETE("package/delete/packageId")
    suspend fun deletePackage(
        @Query(value = "packageId") packageId: String,
    ): Response<ResponseBody>

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    @GET("review/")
    suspend fun getReviewOfPackage(
        @Query(value = "packageID") packageId: String,
    ): Response<Review>

    @GET("review/transporter/")
    suspend fun getReviewsOfTransporter(
        @Query(value = "transporterID") transformerId: String
    ): Response<List<Review>>

    @POST("review/create")
    suspend fun crateReview(
        @Body review: Review,
    ): Response<ResponseBody>

    @PUT("review/edit/")
    suspend fun updateReview(
        @Query(value = "reviewID") reviewID: String,
        @Body review: Review
    ): Response<ResponseBody>

    @DELETE("review/delete/")
    suspend fun deleteReview(
        @Query(value = "reviewID") reviewID: String
    ): Response<ResponseBody>


    //--------------------------------------
    //      OFFER
    //--------------------------------------

    @GET("offer/")
    suspend fun getOffersOnPackage(
        @Query(value="id") packageId: String,
    ): Response<List<Offer>>

    @POST("offer/create/")
    suspend fun createOffer(
        @Query(value = "packageId") packageId: String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @POST("offer/accept/")
    suspend fun acceptOffer(
        @Query(value="packageId") packageId: String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @PUT("offer/edit/")
    suspend fun updateOffer(
        @Query(value = "offerId") offerID:String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @DELETE("offer/delete/")
    suspend fun deleteOffer(
        @Query(value = "offerId") offerID: String
    ): Response<ResponseBody>

}