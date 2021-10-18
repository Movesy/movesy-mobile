package hu.bme.aut.movesy.dagger

import dagger.Component
import hu.bme.aut.movesy.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}