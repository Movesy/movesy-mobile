package hu.bme.aut.movesy


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import hu.bme.aut.movesy.data.MockWebServerResponses

import hu.bme.aut.movesy.network.RestApiInterface
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking
import org.junit.Before


@RunWith(JUnit4::class)
class RetrofitPackageTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    private lateinit var api: RestApiInterface

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/package/")
        api = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()))
            .build().create(RestApiInterface::class.java)
    }

     @Test
     fun `get a package`(){
         runBlocking {
             mockWebServer.enqueue(MockResponse().setBody(MockWebServerResponses.packageResponse).setResponseCode(HttpURLConnection.HTTP_OK))
             val response = api.getPackage("61697379b0e07f5ef70cbb77")
             assert(response.isSuccessful)
             val p = response.body()
             assert(p != null)
         }
     }

    @Test
    fun `checking the package id`(){
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(MockWebServerResponses.packageResponse).setResponseCode(HttpURLConnection.HTTP_OK))
            val response = api.getPackage("61697379b0e07f5ef70cbb77")
            assert(response.body()!!.id == "61697379b0e07f5ef70cbb77")
        }
    }

    @Test
    fun `get an error`(){
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(MockWebServerResponses.packageResponse).setResponseCode(HttpURLConnection.HTTP_NOT_FOUND))
            val response = api.getPackage("61697379b0e07f5ef70cbb77")
            assert(!response.isSuccessful)
        }

    }



    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}