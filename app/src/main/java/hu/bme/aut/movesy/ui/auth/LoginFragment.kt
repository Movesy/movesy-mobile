package hu.bme.aut.movesy.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.FragmentLoginBinding
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.network.TokenInterceptor
import hu.bme.aut.movesy.viewmodel.Status
import hu.bme.aut.movesy.viewmodel.UserUtils
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(){
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var tokenInterceptor: TokenInterceptor

    @Inject
    lateinit var userUtils: UserUtils

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        Log.d("debug", tokenInterceptor.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.login_register_pressed_action)
        }

        binding.btnLogin.setOnClickListener {
            val user = User("",
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                "","","",""
                )
            Log.d("debug", user.toString())

            binding.loginPb.visibility = View.VISIBLE

            viewModel.validateLoginInformation(user).observe(viewLifecycleOwner, {
                when(it.status){
                    Status.SUCCESS -> {
                        Log.d("debug","succesful login, token: ${it.data.toString()}")
                        userUtils.token = it.data!!
                        Log.d("debug", userUtils.token.toString())
                        tokenInterceptor.token = userUtils.getToken()!!
                        navController.navigate(R.id.on_login_action)
                    }
                    Status.LOADING -> {
                        binding.loginPb.visibility = View.VISIBLE
                        Log.d("debug", "loading login")
                    }
                    Status.ERROR -> {
                        Log.d("debug", "error on login")
                        binding.loginPb.visibility = View.GONE
                        binding.etEmail.error = "Invalid Email or Password"
                        binding.etPassword.error = "Invalid Email or Password"
                    }
                }
            })
        }
    }
}