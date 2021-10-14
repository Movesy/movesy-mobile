package hu.bme.aut.movesy.model

data class Package (
    var id: String,
    var userID: String,
    var transporterID: String,
    var from: String,
    var to: String,
    var deadline: String,
    var price: Int,
    var weight: Double,
    var size: String,
    var status: String
)