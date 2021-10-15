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

    @POST("login")
    fun loginUser(
        @Body user: User,
    ): Call<ResponseBody>

    @POST("register")
    fun registerUser(
        @Body user: User,
    ): Call<ResponseBody>

    //--------------------------------------
    //      USER
    //--------------------------------------

    @GET("user/{userId}")
    fun getUser(
        @Path(value = "userId") userId: String,
    ): Call<User>

    @GET("allusers")
    fun getAllUsers(): Call<List<User>>

    @PUT("user/edit/{userId}")
    fun updateUser(
        @Path(value = "userId") userId: String,
        @Body user: User,
    ): Call<ResponseBody>

    @DELETE("user/delete/{userId}")
    fun deleteUser(
        @Path(value = "userId") userId: String,
    ): Call<ResponseBody>

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    @GET("package/all")
    fun getAllPackages(): Call<List<Package>>

    @GET("package/{packageId}")
    fun getPackage(
        @Path(value = "packageId") packageId: String,
    ): Call<Package>

    @GET("package/user/userId")
    fun getPackagesOfUser(
        @Path(value = "userId") packageId: String,
    ): Call<List<Package>>

    @GET("package/transporter/transporterId")
    fun getPackagesOfTransporter(
        @Path(value= "transporterId") transformerId: String,
    ): Call<List<Package>>

    @PUT("package/edit/{packageId}")
    fun editPackage(
        @Path(value="packageId") packageId: String,
        @Body packageToEdit: Package
    ): Call<ResponseBody>

    @DELETE("package/delete/packageId")
    fun deletePackage(
        @Path(value = "packageId") packageId: String,
    ): Call<ResponseBody>

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    @GET("review/{packageID}")
    fun getReviewOfPackage(
        @Path(value = "packageID") packageId: String,
    ): Call<Review>

    @GET("review/transporter/{transporterID}")
    fun getReviewsOfTransporter(
        @Path(value = "transporterID") transformerId: String
    ): Call<List<Review>>

    @POST("review/create")
    fun crateReview(
        @Body review: Review,
    ): Call<ResponseBody>

    @PUT("review/edit/{reviewID}")
    fun updateReview(
        @Path(value = "reviewID") reviewID: String,
        @Body review: Review
    ): Call<ResponseBody>

    @DELETE("review/delete/{reviewID}")
    fun deleteReview(
        @Path(value = "reviewID") reviewID: String
    ): Call<ResponseBody>


    //--------------------------------------
    //      OFFER
    //--------------------------------------

    @GET("offer/{packageId}")
    fun getOffersOnPackage(
        @Path(value="packageId") packageId: String,
    ): Call<List<Offer>>

    @POST("offer/create/{packageId}")
    fun createOffer(
        @Path(value = "packageId") packageId: String,
        @Body offer: Offer
    ): Call<ResponseBody>

    @POST("offer/accept/{packageId}")
    fun acceptOffer(
        @Path(value="packageId") packageId: String,
        @Body offer: Offer
    ): Call<ResponseBody>

    @PUT("offer/edit/{offerId}")
    fun editOffer(
        @Path(value = "offerId") offerID:String,
        @Body offer: Offer
    ): Call<ResponseBody>

    @DELETE("offer/delete/{offerId}")
    fun deleteOffer(
        @Path(value = "offerId") offerID: String
    ):Call<ResponseBody>

}