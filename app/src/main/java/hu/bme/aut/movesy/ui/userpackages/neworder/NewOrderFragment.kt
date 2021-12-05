package hu.bme.aut.movesy.ui.userpackages.neworder

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
import hu.bme.aut.movesy.model.PackageTransferObject
import hu.bme.aut.movesy.utils.*
import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NewOrderFragment : Fragment() {

    private lateinit var binding: NewOrderBinding
    private val viewModel: NewOrderViewModel by viewModels()
    @Inject
    lateinit var userUtils: UserUtils
    private var mode = MODE_CREATE

    companion object{
        const val MODE_CREATE = 0
        const val MODE_EDIT = 1
        var packageToEdit: Package? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewOrderBinding.inflate(inflater, container, false)
        val mode = requireArguments()["MODE"]
        this.mode = if(mode == "CREATE") MODE_CREATE else MODE_EDIT
        if(this.mode == MODE_EDIT){
            changeViewToEditMode()
        }
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
                            if(this.mode == MODE_CREATE)
                                createOrder(createNewPackage(it))
                            else
                                editOrder(collectEditedPackageData(it))
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

    private fun createNewPackage(geocode: Resource<Pair<Location?, Location?>>): PackageTransferObject {
        return  PackageTransferObject(
            null,
            userUtils.getUser()!!.id,
            name = binding.etPackageName.text.toString(),
            deadline = convertToSimpleDateFormat("${binding.dpDeadline.year}-${binding.dpDeadline.month +1}-${binding.dpDeadline.dayOfMonth}"),
            from = geocode.data!!.first,
            to = geocode.data.second,
            status = PackageStatus.WAITING_FOR_REVIEW,
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
            creationDate = getcurrentDateAndTime(),
            username = null,
            transporterName = null,
        )
    }


    private fun checkValidity() : Boolean{
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

    private fun createOrder(newPackage : PackageTransferObject){
        Log.d("debug", newPackage.toString())
        viewModel.addNewOrder(newPackage).observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.LOADING -> {
                    binding.pbCreatePackage.visibility = View.VISIBLE }
                Status.ERROR -> {
                    Toast.makeText(context, "Failed to create new package", Toast.LENGTH_LONG).show()
                    Log.e("network", "Failed to create package: ${response.message}")
                }
                Status.SUCCESS -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
                        .navigate(R.id.on_order_selected_global_action)
                }
            }
        })
    }

    private fun changeViewToEditMode(){
        binding.btnSubmitOrder.text = "Confirm edit"
        var currentPackage = packageToEdit ?: throw Exception("No package provided statically")
        binding.etFrom.setText(currentPackage.from?.address)
        binding.etTo.setText(currentPackage.to?.address)
        binding.etPackageName.setText(currentPackage.name)
        binding.includedOrderPanel.etWeight.setText(currentPackage.weight.toString())
        when(currentPackage.size){
            "SMALL" -> binding.includedOrderPanel.rdbtnSmall.isSelected = true
            "MEDIUM" -> binding.includedOrderPanel.rdbtnMedium.isSelected = true
            "BIG" -> binding.includedOrderPanel.rdbtnBig.isSelected = true
            "HUGE" -> binding.includedOrderPanel.rdbtnHuge.isSelected = true
        }
        if(currentPackage.deadline != null){
            var splittedString = convertToSimpleDateFormat(currentPackage.deadline!!).split("-")
            binding.dpDeadline.updateDate(splittedString[0].toInt(), splittedString[1].toInt(), splittedString[2].toInt())
        }
    }

    fun collectEditedPackageData(geocode: Resource<Pair<Location?, Location?>>): Package {
        return  Package(
            packageToEdit!!.id,
            userUtils.getUser()!!.id,
            name = binding.etPackageName.text.toString(),
            deadline = "${binding.dpDeadline.year}-${binding.dpDeadline.month}-${binding.dpDeadline.dayOfMonth}",
            from = geocode.data!!.first,
            to = geocode.data.second,
            status = packageToEdit?.status,
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
            transporterID = packageToEdit?.transporterID,
            price = packageToEdit?.price,
            creationDate = getcurrentDateAndTime(),
            username = packageToEdit?.username,
            transporterName = packageToEdit?.transporterName,
        )
    }

    private fun editOrder(newPackage : Package){
        viewModel.editPackage(newPackage).observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.LOADING -> { binding.pbCreatePackage.visibility = View.VISIBLE }
                Status.ERROR -> {
                    Toast.makeText(context, "Failed to edit package", Toast.LENGTH_LONG).show()
                    Log.e("network", "Failed to edit package: ${response.message}")
                }
                Status.SUCCESS -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
                        .navigate(R.id.on_order_selected_global_action)
                }
            }
        })
    }
}

