package hu.bme.aut.movesy.model

data class OfferInfo(
    var id: String,
    var packageID: String,
    var transporterName: String,
    var transporterID: String,
    var price: Int,
)
