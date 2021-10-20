package hu.bme.aut.movesy.dagger

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import hu.bme.aut.movesy.network.RestAPI
import javax.inject.Singleton
import android.preference.PreferenceManager

import android.app.Application

import android.content.SharedPreferences
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.movesy.network.RestApiInterface
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
    fun provideRetrofit(moshi: Moshi): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(RestAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    @Provides
    @Singleton
    fun provideRestApi(retrofit: Retrofit): RestApiInterface = retrofit.create(RestApiInterface::class.java)

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

}