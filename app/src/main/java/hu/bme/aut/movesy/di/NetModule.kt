package hu.bme.aut.movesy.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import hu.bme.aut.movesy.network.RestAPI
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.movesy.network.RestApiInterface
import hu.bme.aut.movesy.network.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class NetModule() {

    @Provides
    @Singleton
    fun provideMoshi() :Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttp(tokenInterceptor: TokenInterceptor): OkHttpClient{
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitWithAuth(moshi: Moshi, httpClient: OkHttpClient): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(RestAPI.BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideRestApi( retrofit: Retrofit): RestApiInterface = retrofit.create(RestApiInterface::class.java)

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application?): SharedPreferences {
        return application?.getSharedPreferences("AUTH", Context.MODE_PRIVATE)
            ?: throw Exception("Application not running, cannot get context")
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context)= context

}