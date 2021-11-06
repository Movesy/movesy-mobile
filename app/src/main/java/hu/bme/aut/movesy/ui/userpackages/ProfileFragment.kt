package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import hu.bme.aut.movesy.databinding.ProfileTransporterDataBinding
import hu.bme.aut.movesy.databinding.ProfileUserDataBinding

class ProfileFragment : Fragment() {


    private lateinit var binding: ProfileTransporterDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileTransporterDataBinding.inflate(inflater, container, false)


        return binding.root
    }
}