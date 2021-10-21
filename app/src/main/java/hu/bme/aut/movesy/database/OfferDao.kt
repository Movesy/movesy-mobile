package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.movesy.model.Offer

@Dao
interface OfferDao {

    @Query("SELECT * FROM offers WHERE packageID = :packageId")
    fun getOffersOnPackage(packageId: String): LiveData<List<Offer>>

    @Insert
    suspend fun createOffer(offer: Offer)

    @Update
    suspend fun updateOffer(offer: Offer)

    @Query("DELETE FROM offers WHERE id = :offerId")
    suspend fun deleteOffer(offerId: String)


}