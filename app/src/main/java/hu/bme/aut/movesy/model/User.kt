package hu.bme.aut.movesy.model

data class User (
    var id: String,
    var username: String,
    var password: String,
    var email: String,
    var telephone: String,
    var size: String,
    var role: String
)