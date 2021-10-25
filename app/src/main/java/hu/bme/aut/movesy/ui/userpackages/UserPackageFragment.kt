package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.databinding.LoginBinding
import hu.bme.aut.movesy.viewmodel.Status

@AndroidEntryPoint
class UserPackageFragment: Fragment() {

    private val viewModel: UserPackageViewModel by viewModels()
    private lateinit var binding: LoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        Log.d("status", "asdf")
        binding.btnLogin.setOnClickListener {

        }
    }

    private fun setupObservers(){
        Log.d("status", "FF")
        viewModel.packages.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    Log.d("status", "succes")
                    Log.d("status", it.data.toString())
                }
                Status.ERROR -> {
                    Log.d("status", "error")
                }
                Status.LOADING -> {
                    Log.d("status", "loading")
                }
            }
        })
    }
}