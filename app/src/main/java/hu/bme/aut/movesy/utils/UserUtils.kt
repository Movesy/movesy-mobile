package hu.bme.aut.movesy.utils

import hu.bme.aut.movesy.model.Token
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUtils  @Inject constructor(){
    var token:Token? = null
    fun getUser() = token?.user
    fun getToken() = token?.jwtToken

}