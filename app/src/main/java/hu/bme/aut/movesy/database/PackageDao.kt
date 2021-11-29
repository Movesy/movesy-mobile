package hu.bme.aut.movesy.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.model.User


@Dao
interface PackageDao {

    @Query("SELECT * FROM packages")
    fun getAllPackages(): LiveData<List<Package>>

    @Query("SELECT * FROM packages WHERE id = :packageId")
    fun getPackage(packageId: String): LiveData<Package>

    @Query("SELECT * FROM packages WHERE id = :packageId")
    fun getPackageForExistence(packageId: String): Package?

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

    suspend fun updateOrInsert(pack: Package) {
        getPackageForExistence(pack.id) ?: return createPackage(pack)
        Log.d("db", "Updating package: $pack")
        updatePackage(pack)
    }

    @Query("SELECT * FROM packages WHERE userID = :userId")
    fun getPackagesOfUserBlocking(userId: String): MutableList<Package>

    @Query("SELECT * FROM packages WHERE transporterID = :transporterId")
    fun getPackagesOfTransporterBlocking(transporterId: String): MutableList<Package>

    @Query("SELECT * FROM packages")
    fun getAllPackagesBlocking(): MutableList<Package>

    suspend fun updatePackageTableOfUser(pack: List<Package>, userId: String){
        val currentPackages = getPackagesOfUserBlocking(userId)
        currentPackages.removeAll(pack)
        currentPackages.forEach { p -> deletePackage(p.id) }
        pack.forEach { p -> updateOrInsert(p) }
    }

    suspend fun updatePackageTableOfTransporter(pack: List<Package>, transporterId: String){
        val currentPackages = getPackagesOfTransporterBlocking(transporterId)
        currentPackages.removeAll(pack)
        currentPackages.forEach { p -> deletePackage(p.id) }
        pack.forEach { p -> updateOrInsert(p) }
    }

    suspend fun updatePackageTable(pack: List<Package>){
        val currentPackages = getAllPackagesBlocking()
        currentPackages.removeAll(pack)
        currentPackages.forEach { p -> deletePackage(p.id) }
        pack.forEach { p -> updateOrInsert(p) }
    }
}