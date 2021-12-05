package hu.bme.aut.movesy.ui.userpackages.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Resource
import hu.bme.aut.movesy.utils.UserUtils
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUtils: UserUtils,
    private val repository: Repository,
): ViewModel() {

    fun saveUser(user: User): LiveData<Resource<ResponseBody>> {
        Log.d("debug", user.toString())
        return repository.updateUser(user)
    }

}