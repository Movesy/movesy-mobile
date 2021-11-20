package hu.bme.aut.movesy.ui.userpackages

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.movesy.databinding.OfferDialogBinding

class AddOfferDialogFragment: DialogFragment() {
    private lateinit var binding: OfferDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OfferDialogBinding.inflate(layoutInflater, container, false)
        binding.btnCancel.setOnClickListener {

        }

        return binding.root
    }

}