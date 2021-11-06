package hu.bme.aut.movesy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.databinding.ActivityMainBinding
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.network.ResponseHandler
import hu.bme.aut.movesy.network.RestAPI
import hu.bme.aut.movesy.network.RestApiInterface
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.ui.userpackages.UserPackageFragment
import hu.bme.aut.movesy.ui.userpackages.UserPackageViewModel
import hu.bme.aut.movesy.viewmodel.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    @Inject lateinit var repository: Repository
    @Inject lateinit var restAPI: RestAPI
    @Inject lateinit var inter: RestApiInterface
    private val viewModel: UserPackageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //var api = RestAPI()
        val a = UserPackageFragment()


        var user = User("", "asdfasdfasdf", "123123", "asdfasdf@gmail.com", "+36306550798", size = "", role="customer" )
        //api.registerUser(user, ResponseHandler(baseContext))
       //api.getAllUser(ResponseHandler(baseContext){
       //    Log.d("res", it.toString())
       //})
        //var b = repository.getAllPackages()
        //binding.textView.text = b.value?.data.toString()
       // Log.d("status",b.value?.status.toString() )

    }

    override fun onStart() {
        super.onStart()
        //setupObservers()
    }

    //private fun setupObservers(){
    //    Log.d("status", "FF")
    //    viewModel.packages.observe(this, {
    //        when(it.status){
    //            Status.SUCCESS -> {
    //                binding.textView.text = it.data.toString()
    //                Log.d("status", "succes")
    //                Log.d("status", it.data.toString())
    //            }
    //            Status.ERROR -> {
    //                Log.d("status", "error")
    //            }
    //            Status.LOADING -> {
    //                Log.d("status", "loading")
    //            }
    //        }
    //    })
    //}
}