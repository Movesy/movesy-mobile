package hu.bme.aut.movesy.ui.auth

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.RegisterBinding
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt

class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterBinding.inflate(inflater, container, false)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.on_register_action)
        }
        return binding.root
    }

}