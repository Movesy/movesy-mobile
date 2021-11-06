package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import hu.bme.aut.movesy.databinding.OffersListFragmentBinding
import hu.bme.aut.movesy.viewmodel.Status


class UserOfferFragment : Fragment(){

    private lateinit var binding:OffersListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OffersListFragmentBinding.inflate(inflater, container, false)



        return binding.root
    }
}