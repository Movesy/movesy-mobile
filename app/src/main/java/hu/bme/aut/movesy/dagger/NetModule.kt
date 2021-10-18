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


@Module
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
    fun provideRestAPI(moshi:Moshi): RestAPI {
        return RestAPI(moshi)
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

}