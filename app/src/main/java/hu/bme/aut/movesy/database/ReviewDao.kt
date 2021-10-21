package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.movesy.model.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE packageID = :packageId")
    fun getReviewOfPackage(packageId: String): LiveData<List<Review>>

    @Query("SELECT * FROM reviews WHERE transporterID = :transporterId")
    fun getReviewOfTransporter(transporterId: String): LiveData<List<Review>>

    @Insert
    fun createReview(review: Review)

    @Update
    fun updateReview(review: Review)

    @Delete
    fun deleteReview(review: Review)
}