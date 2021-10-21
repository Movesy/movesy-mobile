package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.*

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
    fun createPackage(newPackage: Package)

    @Update
    fun updatePackage(newPackage: Package)

    @Delete
    fun deletePackage(deletePackage: Package)
}