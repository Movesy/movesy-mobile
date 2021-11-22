package hu.bme.aut.movesy.model

data class Token (
    val user: User,
    val jwtToken: String
    )