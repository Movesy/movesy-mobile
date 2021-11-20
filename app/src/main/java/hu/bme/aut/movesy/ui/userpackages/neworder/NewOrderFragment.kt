package hu.bme.aut.movesy.ui.userpackages

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.NewOrderBinding
import hu.bme.aut.movesy.model.Location
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.ui.userpackages.neworder.NewOrderViewModel
import hu.bme.aut.movesy.viewmodel.Resource
import hu.bme.aut.movesy.viewmodel.Status
import hu.bme.aut.movesy.viewmodel.UserUtils
import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NewOrderFragment : Fragment() {

    private lateinit var binding: NewOrderBinding
    private val viewModel: NewOrderViewModel by viewModels()
    @Inject
    lateinit var userUtils: UserUtils


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewOrderBinding.inflate(inflater, container, false)

        binding.btnSubmitOrder.setOnClickListener {

            if(checkValidity().not()) return@setOnClickListener
            viewModel.geocodeFromAndTo(
                requireContext(),
                binding.etFrom.text.toString(),
                binding.etTo.text.toString()
            )
                .observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            createOrder(createNewPackage(it))
                        }
                        Status.ERROR -> {
                            binding.etFrom.error = it.message
                            binding.etTo.error = it.message
                        }
                    }

                })
        }
        return binding.root
    }

    fun createNewPackage(geocode: Resource<Pair<Location?, Location?>>): Package {
        val calendar = Calendar.getInstance()
        val p =  Package(
            "",
            userUtils.getUser()!!.id,
            name = binding.etPackageName.text.toString(),
            deadline = "${binding.dpDeadline.year}-${binding.dpDeadline.month}-${binding.dpDeadline.dayOfMonth}",
            from = geocode.data!!.first,
            to = geocode.data.second,
            status = "1",
            weight = binding.includedOrderPanel.etWeight.text.toString()
                .toDouble(),
            size =
            when (binding.includedOrderPanel.radioGroup.checkedRadioButtonId) {
                binding.includedOrderPanel.rdbtnSmall.id -> "SMALL"
                binding.includedOrderPanel.rdbtnSmall.id -> "MEDIUM"
                binding.includedOrderPanel.rdbtnSmall.id -> "BIG"
                binding.includedOrderPanel.rdbtnSmall.id -> "HUGE"
                else -> "HUGE"
            },
            transporterID = null,
            price = null,
            creationDate = getcurrentDateAndTime()
        )

        Log.d(
            "New package created. source: NewOrderFragment \n",
            p.toString()
        )

        return p
    }

    fun getcurrentDateAndTime(): String {
        val c = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(c)
    }

    fun checkValidity() : Boolean{
        var valid = true

        if(binding.includedOrderPanel.etWeight.text.isBlank()){
            binding.includedOrderPanel.etWeight.error = "You must fill out this field!"
            valid = false
        }else {
            try {
                binding.includedOrderPanel.etWeight.text.toString().toDouble()
            }catch (e: NumberFormatException){
                binding.includedOrderPanel.etWeight.error = "You must give a number!"
                valid = false
            }
        }

        if(binding.etPackageName.text.isBlank()){
            binding.etPackageName.error = "You must fill out this field!"
            valid = false
        }else if((5 < binding.etPackageName.text.toString().length || binding.etPackageName.text.toString().length < 30).not()){
            binding.etPackageName.error = "Package name must be between 5 and 30 characters"
        }

        if(binding.etFrom.text.isBlank()){
            binding.etFrom.error = "You must fill out this field!"
            valid = false
        }

        if(binding.etTo.text.isBlank()){
            binding.etTo.error = "You must fill out this field!"
            valid = false
        }


        return valid
    }

    fun createOrder(newPackage : Package){
        viewModel.addNewOrder(newPackage)
            .observe(viewLifecycleOwner, { response ->
                when (response.status) {
                    Status.LOADING -> {
                        binding.pbCreatePackage.visibility = View.VISIBLE
                        Log.d(
                            "network",
                            "Sent package to server, waiting for response..."
                        )
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            context,
                            "Failed to create new package",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(
                            "network",
                            "Failed to create package: ${response.message}"
                        )
                    }
                    Status.SUCCESS -> {
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.nav_orders_fragment_container
                        )
                            .navigate(R.id.on_order_selected_global_action)
                        Log.d("network", "Successfully created new package")
                    }
                }
            })
    }
}

