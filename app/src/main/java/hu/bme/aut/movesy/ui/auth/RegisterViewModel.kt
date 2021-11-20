package hu.bme.aut.movesy.ui.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.repository.Repository
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun registerUser(user: User) = repository.registerUser(user)
}