package hu.bme.aut.movesy.ui.userpackages.addoffer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.databinding.OfferDialogBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.ui.userpackages.availableoffers.AvailableOffersViewModel
import hu.bme.aut.movesy.utils.Status
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddOfferDialogFragment: DialogFragment() {
    private lateinit var binding: OfferDialogBinding
    private val viewModel: AddOfferViewModel by viewModels()
    private var packagePrice: Int? = null
    private lateinit var packageID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OfferDialogBinding.inflate(layoutInflater, container, false)
        packageID =  requireArguments()["PACKAGE_ID"].toString()
        packagePrice =  requireArguments()["PACKAGE_PRICE"].toString().toInt()

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSend.setOnClickListener {
            if(validateAmountField()){
                sendOfferAndDismiss()
            }
        }

        return binding.root
    }

    private fun validateAmountField(): Boolean{
        if(binding.editTextNumber.text.isEmpty()){
            binding.editTextNumber.error = "Please set a price"
            return false
        }
        if(packagePrice != null && packagePrice != 0 &&
            binding.editTextNumber.text.toString().toInt() >= packagePrice!!)
        {
            binding.editTextNumber.error = "Must set a lower price than $packagePrice"
            return false
        }
        return true
    }

    private fun sendOfferAndDismiss(){
        val price = binding.editTextNumber.text.toString().toInt()
        viewModel.sendOffer(packageID, price).observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.pbDialog.visibility = View.GONE
                    Snackbar.make(this.requireView(), "Offer successfully sent", Snackbar.LENGTH_SHORT).show()
                    dismiss()
                }
                Status.ERROR -> {
                    binding.pbDialog.visibility = View.GONE
                    Toast.makeText(context, "An unknown error has occurred", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.pbDialog.visibility = View.VISIBLE
                }
            }
        }
    }

}