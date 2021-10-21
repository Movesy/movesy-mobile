package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.movesy.model.Package


@Dao
interface PackageDao {

    @Query("SELECT * FROM packages")
    fun getAllPackages(): LiveData<List<Package>>

    @Query("SELECT * FROM packages WHERE id = :packageId")
    fun getPackage(packageId: String): LiveData<Package>

    @Query("SELECT * FROM packages WHERE userID = :userId")
    fun getPackagesOfUser(userId: String): LiveData<List<Package>>

    @Query("SELECT * FROM packages WHERE transporterID = :transporterId")
    fun getPackagesOfTransporter(transporterId: String): LiveData<List<Package>>

    @Insert
    suspend fun createPackage(newPackage: Package)

    @Update
    suspend fun updatePackage(newPackage: Package)

    @Query("DELETE FROM packages WHERE id = :packageId")
    suspend fun deletePackage(packageId: String)
}