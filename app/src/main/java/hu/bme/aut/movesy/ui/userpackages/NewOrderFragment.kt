package hu.bme.aut.movesy.ui.userpackages

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
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.network.Geocoder
import hu.bme.aut.movesy.viewmodel.Status
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class NewOrderFragment : Fragment(){

    private lateinit var binding: NewOrderBinding
    private val viewModel: NewOrderViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewOrderBinding.inflate(inflater, container, false)

        binding.btnSubmitOrder.setOnClickListener {
            val calendar = Calendar.getInstance()
           //val from = Geocoder(requireContext(), binding.etFrom.text.toString()).geocode()
           //from.observe(viewLifecycleOwner, {
           //    when(it.status){
           //        Status.SUCCESS -> {Log.d("debug", "success: ${it.data!!}")}
           //        Status.LOADING -> {Log.d("debug", "loading:")}
           //        Status.ERROR -> {Log.d("debug", "success: ${it.message!!}")}
           //    }
           //})
           //val to = Geocoder(requireContext(),binding.etTo.text.toString()).geocode()
            viewModel.geocodeFromAndTo(
                requireContext(),
                binding.etFrom.text.toString(),
                binding.etTo.text.toString())
                .observe(viewLifecycleOwner, {
                    if(it.first == null || it.second == null || it.first!!.latitude == 0.0 || it.second!!.latitude == 0.0)
                        return@observe
                    val newPackage = Package(
                        "",
                        "616965d764e7d8734b1a518d",
                        name = binding.etPackageName.text.toString(),
                        deadline = "${binding.dpDeadline.year}-${binding.dpDeadline.month}-${binding.dpDeadline.dayOfMonth}",
                        from = it.first,
                        to = it.second,
                        date = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DATE)}",
                        status = "4",
                        weight = binding.includedOrderPanel.etWeight2.text.toString().toDouble(),
                        size = "HUGE",
                        transporterID = null,
                        price = 123
                    )
                    Log.d("debug", newPackage.toString())
                    viewModel.addNewOrder(newPackage).observe(viewLifecycleOwner, { response ->
                        when (response.status) {
                            Status.LOADING -> {
                                binding.pbCreatePackage.visibility = View.VISIBLE
                                Log.d("network", "Sent package to server, waiting for response...")
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Failed to create new package",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("network", "Failed to create package: ${response.message}")
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
                })
            }
        return binding.root
    }
}