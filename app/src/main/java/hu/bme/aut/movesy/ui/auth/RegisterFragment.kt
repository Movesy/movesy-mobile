package hu.bme.aut.movesy.ui.auth

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.RegisterBinding
import hu.bme.aut.movesy.model.User
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt

class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

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

        if (binding.swFreelancer.isActivated){
            role = "TRANSPORTER"
        }

        val user = User(
            username = binding.etUsername.text.toString(),
            password = binding.etPassword.text.toString(),
            email = binding.etEmail.text.toString(),
            telephone = binding.etRegisterPhone.toString(),
            id = "",
            size = "HUGE",
            role = role
        )
    }

}