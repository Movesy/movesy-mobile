package hu.bme.aut.movesy.ui.userpackages.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUtils: UserUtils,
    private val repository: Repository,
): ViewModel() {

    fun saveUser(user: User) = repository.updateUser(user)

}