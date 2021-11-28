package hu.bme.aut.movesy.ui.auth


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.RegisterBinding
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.network.TokenInterceptor
import hu.bme.aut.movesy.utils.Status
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var tokenInterceptor: TokenInterceptor

    @Inject
    lateinit var userUtils: UserUtils

    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)
        binding.btnRegisterUser.setOnClickListener{
            if(validate().not()) return@setOnClickListener
            registerUser()
        }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         navController = Navigation.findNavController(view)
    }

    fun validate(): Boolean {
        var valid = true
        if (binding.etUsername.text.isBlank()) {
            binding.etUsername.error = "You must fill out this field!"
            valid = false
        }

        if (binding.etEmail.text.isBlank()) {
            binding.etEmail.error = "You must fill out this field!"
            valid = false
        }

        if (binding.etPassword.text.isEmpty()) {
            binding.etPassword.error = "You must fill out this field!"
            valid = false
        }

        if (binding.etConfirmPassword.text.isEmpty()) {
            binding.etPassword.error = "You must fill out this field!"
            valid = false
        }else if (binding.etPassword.text.toString().equals(binding.etConfirmPassword.text.toString()).not()){
            binding.etConfirmPassword.error= "This does not equal with the given password!"
        }

        if(binding.etRegisterPhone.text.isBlank()){
            binding.etRegisterPhone.error = "You must fill out this field!"
        }
            return valid
    }

    fun registerUser(){

        var role:String = "USER"

        if (binding.swFreelancer.isChecked){
            role = "TRANSPORTER"
        }

        val user = User(
            username = binding.etUsername.text.toString(),
            password = binding.etPassword.text.toString(),
            email = binding.etEmail.text.toString(),
            telephone = binding.etRegisterPhone.text.toString(),
            id = "",
            size = "HUGE",
            role = role
        )

        Log.d("registeruser", user.toString())

        viewModel.registerUser(user).observe(viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    loginUser(user)
                }
            }
        }
    }

    fun loginUser(user: User){

        viewModel.validateLoginInformation(user).observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    Log.d("debug","succesful login, token: ${it.data.toString()}")
                    userUtils.token = it.data!!
                    Log.d("debug", userUtils.token.toString())
                    tokenInterceptor.token = userUtils.getToken()!!
                    navController.navigate(R.id.on_register_action)
                }
                Status.LOADING -> {
                    Log.d("debug", "loading login")
                }
                Status.ERROR -> {
                    Log.d("debug", "error on login")
                }
            }
        })
    }

}