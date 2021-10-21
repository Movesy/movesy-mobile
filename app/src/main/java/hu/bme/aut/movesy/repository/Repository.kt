package hu.bme.aut.movesy.repository

import hu.bme.aut.movesy.database.OfferDao
import hu.bme.aut.movesy.database.PackageDao
import hu.bme.aut.movesy.database.ReviewDao
import hu.bme.aut.movesy.database.UserDao
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.network.RestAPI
import hu.bme.aut.movesy.viewmodel.performGetOperation
import hu.bme.aut.movesy.viewmodel.performPostOperation
import javax.inject.Inject

class Repository @Inject constructor(
    private val restapi: RestAPI,
    private val userDao: UserDao,
    private val offerDao: OfferDao,
    private val reviewDao: ReviewDao,
    private val packageDao: PackageDao,
    ){

    //--------------------------------------
    //      AUTHENTICATION
    //--------------------------------------

    fun loginUser(user: User) = performPostOperation(
        networkCall = { restapi.loginUser(user)  },
        saveCallResult = { userDao.updateOrInsert(user) }
    )

    fun registerUser(user: User) = performPostOperation(
    networkCall = { restapi.registerUser(user) },
    saveCallResult = { userDao.insertUser(user) }
    )

    //--------------------------------------
    //      USER
    //--------------------------------------

    fun getUser(userID: String) = performGetOperation(
        databaseQuery = { userDao.getUser(userID) },
        networkCall = { restapi.getUser(userID) },
        saveCallResult = {user -> userDao.insertUser(user)}
    )

    fun getAllUser()  = performGetOperation(
        databaseQuery = { userDao.getAllUsers() },
        networkCall = { restapi.getAllUser() },
        saveCallResult = {users -> users.forEach {user -> userDao.updateOrInsert(user) } }
    )

    ///TODO REDUNDÁNS PARAMÉTER FIX
    fun updateUser(user: User) = performPostOperation(
        networkCall = { restapi.updateUser(user) },
        saveCallResult = { userDao.updateUser(user) }
    )

    fun deleteUser(userID: String) = performPostOperation(
        networkCall = { restapi.deleteUser(userID) },
        saveCallResult = { userDao.deleteUser(userID) }
    )

    //--------------------------------------
    //      PACKAGE
    //--------------------------------------

    fun getAllPackages() = performGetOperation(
        databaseQuery = { packageDao.getAllPackages() },
        networkCall = { restapi.getAllPackages() },
        saveCallResult = {packages -> packages.forEach { pack -> packageDao.createPackage(pack) } }
    )

    fun getPackage(packageID: String) = performGetOperation(
        databaseQuery = { packageDao.getPackage(packageID) },
        networkCall = { restapi.getPackage(packageID) },
        saveCallResult = { pack -> packageDao.createPackage(pack) }
    )

    fun getPackagesOfOwner(userID: String) = performGetOperation(
        databaseQuery = { packageDao.getPackagesOfUser(userID) },
        networkCall = { restapi.getPackagesOfOwner(userID) },
        saveCallResult = {packages -> packages.forEach { pack -> packageDao.createPackage(pack) } }
    )

    fun getPackageOfTransporter(transporterID: String) = performGetOperation(
        databaseQuery = { packageDao.getPackagesOfTransporter(transporterID) },
        networkCall = { restapi.getPackageOfTransporter(transporterID) },
        saveCallResult = {packages -> packages.forEach { pack -> packageDao.createPackage(pack) } }
    )

    fun createPackage(newPackage: Package) = performPostOperation(
        networkCall = { restapi.createPackage(newPackage) },
        saveCallResult = { packageDao.createPackage(newPackage) }
    )

    fun updatePackage(packageToEdit: Package) = performPostOperation(
        networkCall = { restapi.updatePackage(packageToEdit) },
        saveCallResult = { packageDao.updatePackage(packageToEdit) }
    )

    suspend fun deletePackage(packageID: String) = performPostOperation(
        networkCall = { restapi.deletePackage(packageID) },
        saveCallResult = { packageDao.deletePackage(packageID) }
    )

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    suspend fun getReviewOfPackage(packageID: String) = performGetOperation(
        databaseQuery = { reviewDao.getReviewOfPackage(packageID) },
        networkCall = { restapi.getReviewOfPackage(packageID) },
        saveCallResult = {review -> reviewDao.createReview(review) }
    )

    fun getReviewOfTransporter(transporterID: String) = performGetOperation(
        databaseQuery = { reviewDao.getReviewOfTransporter(transporterID) },
        networkCall = { restapi.getReviewOfTransporter(transporterID) },
        saveCallResult = { reviews -> reviews.forEach {review -> reviewDao.createReview(review) } }
    )

    fun createReview(review: Review) = performPostOperation(
        networkCall = { restapi.createReview(review) },
        saveCallResult = { reviewDao.createReview(review) }
    )

    fun editReview(review: Review) = performPostOperation(
        networkCall = { restapi.updateReview(review) },
        saveCallResult = { reviewDao.updateReview(review) }
    )

    fun deleteReview(reviewID : String) = performPostOperation(
        networkCall = { restapi.deleteReview(reviewID) },
        saveCallResult = { reviewDao.deleteReview(reviewID) }
    )

    //--------------------------------------
    //      OFFER
    //--------------------------------------

    fun getOffersOnPackage(packageID: String) = performGetOperation(
        databaseQuery = { offerDao.getOffersOnPackage(packageID) },
        networkCall = { restapi.getOffersOnPackage(packageID) },
        saveCallResult = { offers -> offers.forEach { offer -> offerDao.createOffer(offer) } }
    )

    fun createOffer(offer: Offer) = performPostOperation(
        networkCall = { restapi.createOffer(offer) },
        saveCallResult = { offerDao.createOffer(offer) }
    )

    fun acceptOffer(offer: Offer) = performPostOperation(
        networkCall = { restapi.acceptOffer(offer) },
        saveCallResult = { offerDao.updateOffer(offer) }
    )

    fun updateOffer(offer: Offer) = performPostOperation(
        networkCall = { restapi.updateOffer(offer) },
        saveCallResult = { offerDao.updateOffer(offer) }
    )

    fun deleteOffer(offerID: String) = performPostOperation(
        networkCall = { restapi.deleteOffer(offerID) },
        saveCallResult = { offerDao.deleteOffer(offerID) }
    )

}