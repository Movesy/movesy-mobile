package hu.bme.aut.movesy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.User

@Database(
    entities = [
        Offer::class,
        Package::class,
        Review::class,
        User::class,
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun offerDao(): OfferDao

    abstract fun packageDao(): PackageDao

    abstract fun reviewDao(): ReviewDao

    abstract fun userDao(): UserDao

    companion object {
        fun getDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "appDatabase"
            ).build();
        }
    }
}