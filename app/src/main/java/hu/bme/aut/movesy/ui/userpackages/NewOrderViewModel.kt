package hu.bme.aut.movesy.ui.userpackages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.viewmodel.Resource
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun  addNewOrder(pack: Package): LiveData<Resource<ResponseBody>>{
        return repository.createPackage(pack)
    }
}