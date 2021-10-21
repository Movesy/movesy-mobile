package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Package

@Dao
interface OfferDao {

    @Query("SELECT * FROM offers WHERE packageID = :packageId")
    fun getOffersOnPackage(packageId: String): LiveData<List<Offer>>

    @Query("SELECT * FROM offers WHERE id = :offerId")
    fun getOfferForExistence(offerId: String): Offer?

    @Insert
    suspend fun createOffer(offer: Offer)

    @Update
    suspend fun updateOffer(offer: Offer)

    @Query("DELETE FROM offers WHERE id = :offerId")
    suspend fun deleteOffer(offerId: String)

    suspend fun updateOrInsert(offer: Offer) {
        getOfferForExistence(offer.id) ?: return createOffer(offer)
        updateOffer(offer)
    }
}