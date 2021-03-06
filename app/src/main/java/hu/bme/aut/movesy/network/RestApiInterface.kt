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
        @Body user: UserTransferObject,
    ): Response<User>

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

    @GET("package/user/")
    suspend fun getPackagesOfUser(
        @Query(value = "id") packageId: String,
    ): Response<List<Package>>

    @GET("package/transporter/")
    suspend fun getPackagesOfTransporter(
        @Query(value= "id") transformerId: String,

    ): Response<List<Package>>

    @POST("package/create")
    suspend fun createPackage(
        @Body newPackage: PackageTransferObject
    ): Response<Package>

    @PUT("package/edit/")
    suspend fun updatePackage(
        @Query(value = "id") packageId: String,
        @Body packageToEdit: Package
    ): Response<ResponseBody>

    @DELETE("package/delete/")
    suspend fun deletePackage(
        @Query(value = "id") packageId: String,
    ): Response<ResponseBody?>

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    @GET("review/")
    suspend fun getReviewOfPackage(
        @Query(value = "packageID") packageId: String,
    ): Response<Review>

    @GET("review/transporter/")
    suspend fun getReviewsOfTransporter(
        @Query(value = "id") transformerId: String
    ): Response<List<Review>>

    @POST("review/create")
    suspend fun crateReview(
        @Body review: ReviewTransferObject,
    ): Response<Review>

    @PUT("review/edit/")
    suspend fun updateReview(
        @Body review: Review
    ): Response<Review>

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

    @POST("offer/create")
    suspend fun createOffer(
        @Body offer: OfferTransferObject
    ): Response<Offer>


    @DELETE("offer/accept/")
    suspend fun acceptOffer(
        @Query(value="id") offerID: String,
    ): Response<ResponseBody?>

    @PUT("offer/edit/")
    suspend fun updateOffer(
        @Query(value = "offerId") offerID:String,
        @Body offer: Offer
    ): Response<ResponseBody>

    @DELETE("offer/reject/")
    suspend fun deleteOffer(
        @Query(value = "id") offerID: String
    ): Response<ResponseBody?>

}