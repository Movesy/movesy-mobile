package hu.bme.aut.movesy.dagger

import android.app.Application


class BaseApplication : Application() {
    var networkComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        networkComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
    }
}