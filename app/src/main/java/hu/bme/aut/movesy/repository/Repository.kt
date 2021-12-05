package hu.bme.aut.movesy.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import hu.bme.aut.movesy.database.OfferDao
import hu.bme.aut.movesy.database.PackageDao
import hu.bme.aut.movesy.database.ReviewDao
import hu.bme.aut.movesy.database.UserDao
import hu.bme.aut.movesy.model.*
import hu.bme.aut.movesy.network.RestAPI
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.performGetOperation
import hu.bme.aut.movesy.utils.performPostOperation
import hu.bme.aut.movesy.utils.performPostOperationWithUnsafeBody
import kotlinx.coroutines.Dispatchers
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

    fun loginUser(user: User):LiveData<Resource<Token>> = liveData(Dispatchers.IO) {
        val responseStatus = restapi.loginUser(user)
        emit(responseStatus)
    }


    fun registerUser(user: UserTransferObject) = performPostOperation(
    networkCall = { restapi.registerUser(user) },
    saveCallResult = {newUser ->  userDao.updateOrInsert(newUser) }
    )

    //--------------------------------------
    //      USER
    //--------------------------------------

    fun getUser(userID: String) = performGetOperation(
        databaseQuery = { userDao.getUser(userID) },
        networkCall = { restapi.getUser(userID) },
        saveCallResult = {user -> userDao.updateOrInsert(user)}
    )

    fun getAllUser()  = performGetOperation(
        databaseQuery = { userDao.getAllUsers() },
        networkCall = { restapi.getAllUser() },
        saveCallResult = {users -> users.forEach {user -> userDao.updateOrInsert(user) } }
    )

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
        saveCallResult = {packages -> packageDao.updatePackageTable(packages) }
    )

    fun getPackage(packageID: String) = performGetOperation(
        databaseQuery = { packageDao.getPackage(packageID) },
        networkCall = { restapi.getPackage(packageID) },
        saveCallResult = { pack -> packageDao.updateOrInsert(pack) }
    )

    fun getPackagesOfOwner(userID: String) = performGetOperation(
        databaseQuery = { packageDao.getPackagesOfUser(userID) },
        networkCall = { restapi.getPackagesOfOwner(userID) },
        saveCallResult = { packages -> packageDao.updatePackageTableOfUser(packages, userID) }
    )

    fun getPackageOfTransporter(transporterID: String) = performGetOperation(
        databaseQuery = { packageDao.getPackagesOfTransporter(transporterID) },
        networkCall = { restapi.getPackageOfTransporter(transporterID) },
        saveCallResult = {packages -> packageDao.updatePackageTableOfTransporter(packages, transporterID) }
    )

    fun createPackage(newPackage: PackageTransferObject) = performPostOperation(
        networkCall = { restapi.createPackage(newPackage) },
        saveCallResult = { packageA -> packageDao.createPackage(packageA) }
    )

    fun updatePackage(packageToEdit: Package) = performPostOperation(
        networkCall = { restapi.updatePackage(packageToEdit) },
        saveCallResult = { packageDao.updatePackage(packageToEdit) }
    )
    
    fun deletePackage(packageID: String) = performPostOperationWithUnsafeBody(
        networkCall = { restapi.deletePackage(packageID) },
        saveCallResult = { packageDao.deletePackage(packageID) }
    )

    //-------------------------------------
    //      REVIEW
    //--------------------------------------

    fun getReviewOfPackage(packageID: String) = performGetOperation(
        databaseQuery = { reviewDao.getReviewOfPackage(packageID) },
        networkCall = { restapi.getReviewOfPackage(packageID) },
        saveCallResult = {review -> reviewDao.updateOrInsert(review) }
    )

    fun getReviewOfTransporter(transporterID: String) = performGetOperation(
        databaseQuery = { reviewDao.getReviewOfTransporter(transporterID) },
        networkCall = { restapi.getReviewOfTransporter(transporterID) },
        saveCallResult = { reviews -> reviews.forEach {review -> reviewDao.updateOrInsert(review) } }
    )

    fun createReview(review: ReviewTransferObject) = performPostOperation(
        networkCall = { restapi.createReview(review) },
        saveCallResult = {newReview ->  reviewDao.createReview(newReview) }
    )

    fun editReview(review: Review) = performPostOperation(
        networkCall = { restapi.updateReview(review) },
        saveCallResult = {newReview -> reviewDao.updateReview(newReview) }
    )

    fun deleteReview(reviewID : String) = performPostOperationWithUnsafeBody(
        networkCall = { restapi.deleteReview(reviewID) },
        saveCallResult = { reviewDao.deleteReview(reviewID) }
    )

    //--------------------------------------
    //      OFFER
    //--------------------------------------

    fun getOffersOnPackage(packageID: String) = performGetOperation(
        databaseQuery = { offerDao.getOffersOnPackage(packageID) },
        networkCall = { restapi.getOffersOnPackage(packageID) },
        saveCallResult = { offers -> offers.forEach { offer -> offerDao.updateOrInsert(offer) } }
    )

    fun createOffer(offer: OfferTransferObject) = performPostOperation(
        networkCall = { restapi.createOffer(offer) },
        saveCallResult = {resultOffer ->  offerDao.createOffer(resultOffer) }
    )

    fun acceptOffer(offer: Offer) = performPostOperationWithUnsafeBody(
        networkCall = { restapi.acceptOffer(offer.id) },
        saveCallResult = { offerDao.updateOffer(offer) }
    )

    fun updateOffer(offer: Offer) = performPostOperation(
        networkCall = { restapi.updateOffer(offer) },
        saveCallResult = { offerDao.updateOffer(offer) }
    )

    fun deleteOffer(offerID: String) = performPostOperationWithUnsafeBody(
        networkCall = { restapi.deleteOffer(offerID) },
        saveCallResult = { offerDao.deleteOffer(offerID) }
    )


}