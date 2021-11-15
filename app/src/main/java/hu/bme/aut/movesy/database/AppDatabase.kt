package hu.bme.aut.movesy.database

import android.content.Context
import androidx.room.*
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.model.Package


@Database(
    entities = [
        Offer::class,
        Package::class,
        Review::class,
        User::class,
    ], version = 4
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun offerDao(): OfferDao

    abstract fun packageDao(): PackageDao

    abstract fun reviewDao(): ReviewDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "appDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }

}