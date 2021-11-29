package hu.bme.aut.movesy.ui.userpackages.listorders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.OfferInfo
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.*
import javax.inject.Inject

@HiltViewModel
class UserPackageViewModel @Inject constructor(
     private val repository: Repository,
     private val userUtils: UserUtils
):ViewModel() {

    fun getPackageOfUser():  LiveData<Resource<List<Package>>> =
        object : MediatorLiveData<Resource<List<Package>>>() {
            var _users: List<User>? = null
            var _packages: List<Package>? = null

            init {
                addSource(repository.getAllUser()) { result ->
                    this._users = result.data
                    when(result.status){
                        Status.SUCCESS -> _packages?.let { value = Resource.success(mapUsersToOffers(_users!!, _packages!!)) }
                        Status.LOADING -> value = Resource.loading(emptyList())
                        Status.ERROR -> value = Resource.error(result.message ?: "An unknown error has occurred")
                    }
                }
                addSource(if(userUtils.getUser()!!.role == "TRANSPORTER") repository.getPackageOfTransporter(userUtils.getUser()!!.id)
                          else repository.getPackagesOfOwner(userUtils.getUser()!!.id))
                { result ->
                    this._packages = result.data
                    when(result.status){
                        Status.SUCCESS -> _users?.let { value = Resource.success(mapUsersToOffers(_users!!, _packages!!)) }
                        Status.LOADING -> value = Resource.loading(emptyList())
                        Status.ERROR -> value = Resource.error(result.message ?: "An unknown error has occurred")
                    }
                }
            }
        }

    fun mapUsersToOffers(users: List<User>, packages: List<Package>): List<Package>{
         return packages.map { pack ->
             val transporter = users.find { user -> user.id == pack.transporterID }?.username
             val user = users.find { user -> user.id == pack.userID }?.username
             pack.username = user
             pack.transporterName = transporter
             pack
         }
    }

    fun editPackage(pack: Package) = repository.updatePackage(pack)

    fun deletePackage(pack: Package) = repository.deletePackage(pack.id)

}