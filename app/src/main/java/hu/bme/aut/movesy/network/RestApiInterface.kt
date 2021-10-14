package hu.bme.aut.movesy.network

import hu.bme.aut.movesy.model.Quote
import hu.bme.aut.movesy.model.User
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
    fun getPackageOwner(
        @Path(value = "userId") packageId: String,
    ): Call<User>

    @GET("package/transporter/transporterId")
    fun getPackageTransporter(
        @Path(value= "transporterId") transformerId: String,
    ): Call<User>

    @PUT("package/edit/{packageId}")
    fun editPackage(
        @Path(value="packageId") packageId: String,
    ): Call<ResponseBody>

    @DELETE("package/delete/packageId")
    fun deletePackage(
        @Path(value = "packageId") packageId: String,
    ): Call<ResponseBody>

    //--------------------------------------
    //      REVIEW
    //--------------------------------------

    @GET("quote/{packageId}")
    fun getQuotes(
        @Path(value="packageId") packageId: String,
    ): Call<List<Quote>>

    @POST("quote/create/{packageId}")
    fun createQuote(
        @Path(value = "packageId") packageId: String,
        @Body quote: Quote
    ): Call<ResponseBody>

    @POST("quote/accept/packageId")
    fun acceptQuote(
        @Path(value="packageId") packageId: String,
        @Body quote: Quote
    ): Call<ResponseBody>

    @PUT("quote/edit/{quoteId}")
    fun editQuote(
        @Path(value = "quoteId") quoteId:String,
        @Body quote: Quote
    ): Call<ResponseBody>

    @DELETE("quote/delete/{quoteId}")
    fun deleteQuote(
        @Path(value = "quoteId") quoteId: String
    ):Call<ResponseBody>

}