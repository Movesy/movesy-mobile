package hu.bme.aut.movesy.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.movesy.database.*
import hu.bme.aut.movesy.network.RestAPI
import hu.bme.aut.movesy.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext:Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideOfferDao(db: AppDatabase) = db.offerDao()

    @Singleton
    @Provides
    fun providePackageDao(db:AppDatabase) = db.packageDao()

    @Singleton
    @Provides
    fun provideReviewDao(db: AppDatabase) = db.reviewDao()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideRepository(restAPI: RestAPI,
                          userDao: UserDao,
                          offerDao: OfferDao,
                          reviewDao: ReviewDao,
                          packageDao: PackageDao
    ) = Repository(restAPI, userDao, offerDao, reviewDao, packageDao)
}