package hu.bme.aut.movesy.ui.userpackages.listorders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject

@HiltViewModel
class UserPackageViewModel @Inject constructor(
     private val repository: Repository,
     private val userUtils: UserUtils
):ViewModel() {

    fun getPackagesOfUser(): LiveData<Resource<List<Package>>> {
        userUtils.getUser()?.role?.let { Log.d("debug", it) }
        if(userUtils.getUser()?.role == "TRANSPORTER"){
            Log.d("debug", "getting transporter packages")
            return repository.getPackageOfTransporter(userUtils.getUser()!!.id)
        }
        Log.d("debug", "getting user packages")
        return repository.getPackagesOfOwner( userUtils.getUser()!!.id)

    }
}