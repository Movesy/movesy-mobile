package hu.bme.aut.movesy.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.aut.movesy.model.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI {

    private val restApiInterface: RestApiInterface

    companion object{
        ///TODO replace with proper url
        const val BASE_URL = "https://localhost:8000"
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

    suspend fun loginUser(user: User){
        restApiInterface.loginUser(user)
    }
}