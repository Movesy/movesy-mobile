package hu.bme.aut.movesy.network

import hu.bme.aut.movesy.model.User
import retrofit2.Call
import retrofit2.http.*

interface RestApiInterface {

    @POST("login")
    suspend fun loginUser(
        @Body user: User
    ) : Call<User>

}