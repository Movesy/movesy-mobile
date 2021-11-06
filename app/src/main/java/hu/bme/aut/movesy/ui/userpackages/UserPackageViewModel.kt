package hu.bme.aut.movesy.ui.userpackages

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.repository.Repository
import javax.inject.Inject

@HiltViewModel
class UserPackageViewModel @Inject constructor(
     private val repository: Repository
):ViewModel() {
    val packages = repository.getAllPackages()
}