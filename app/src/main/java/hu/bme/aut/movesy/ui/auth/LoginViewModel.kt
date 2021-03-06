package hu.bme.aut.movesy.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Token
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun  validateLoginInformation(user: User): LiveData<Resource<Token>> {
        return repository.loginUser(user)
    }
}