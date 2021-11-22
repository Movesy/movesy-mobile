package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.Package

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE packageID = :packageId")

    fun getReviewOfPackage(packageId: String): LiveData<Review>


    @Query("SELECT * FROM reviews WHERE transporterID = :transporterId")
    fun getReviewOfTransporter(transporterId: String): LiveData<List<Review>>

    @Query("SELECT * FROM reviews WHERE id = :reviewID")
    fun getReviewForExistence(reviewID: String): Review?

    @Insert
    suspend fun createReview(review: Review)

    @Update
    suspend fun updateReview(review: Review)

    @Query("DELETE FROM reviews WHERE id = :reviewID")
    suspend fun deleteReview(reviewID: String)

    suspend fun updateOrInsert(review: Review) {
        getReviewForExistence(review.id) ?: return createReview(review)
        updateReview(review)
    }
}