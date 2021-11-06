package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.movesy.databinding.NewOrderBinding


class NewOrderFragment : Fragment(){

    private lateinit var binding: NewOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewOrderBinding.inflate(inflater, container, false)


        return binding.root
    }
}