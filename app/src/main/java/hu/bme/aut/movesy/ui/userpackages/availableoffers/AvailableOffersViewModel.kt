package hu.bme.aut.movesy.ui.userpackages.availableoffers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.PackageStatus
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.Status
import javax.inject.Inject

@HiltViewModel
class AvailableOffersViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    fun getAllAvailablePackages(): LiveData<Resource<List<Package>>> =
        object : MediatorLiveData<Resource<List<Package>>>() {
            var _users: List<User>? = null
            var _packages: List<Package>? = null

            init {
                addSource(repository.getAllUser()) { result ->
                    this._users = result.data
                    when(result.status){
                        Status.SUCCESS -> _packages?.let { value = Resource.success(mapUsersToPackages(_users!!, _packages!!)) }
                        Status.LOADING -> value = Resource.loading(emptyList())
                        Status.ERROR -> value = Resource.error(result.message ?: "An unknown error has occurred")
                    }
                }
                addSource(repository.getAllPackages()) { result ->
                    this._packages = result.data
                    when(result.status){
                        Status.SUCCESS -> _users?.let { value = Resource.success(mapUsersToPackages(_users!!, _packages!!)) }
                        Status.LOADING -> value = Resource.loading(emptyList())
                        Status.ERROR -> value = Resource.error(result.message ?: "An unknown error has occurred")
                    }
                }
            }
        }

    fun mapUsersToPackages(users: List<User>, packages: List<Package>) : List<Package>{
        return packages.filter {pack -> pack.status == PackageStatus.WAITING_FOR_REVIEW || pack.status == PackageStatus.SENT} .map { pack ->
            val transporter = users.find { user -> user.id == pack.transporterID }?.username ?: ""
            val user = users.find { user -> user.id == pack.userID }?.username ?: return emptyList()
            Package(
                id = pack.id,
                userID = pack.userID,
                name = pack.name,
                transporterID = pack.transporterID,
                from = pack.from,
                to = pack.to,
                deadline= pack.deadline,
                price = pack.price,
                weight = pack.weight,
                size = pack.size,
                status = pack.status,
                creationDate = pack.creationDate,
                username = user,
                transporterName = transporter
            )
        }
    }
}