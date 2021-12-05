package hu.bme.aut.movesy.ui.userpackages.availableoffers

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
import hu.bme.aut.movesy.adapter.AvailableRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.AvailableOrdersFragmentBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.ui.userpackages.addoffer.AddOfferDialogFragment
import hu.bme.aut.movesy.utils.Status
import hu.bme.aut.movesy.utils.UserUtils
import hu.bme.aut.movesy.utils.sizeSorter
import javax.inject.Inject

@AndroidEntryPoint
class AvailableOffersFragment: Fragment(), AvailableRecyclerViewAdapter.AvailableOrdersClickListener {
    private val viewModel: AvailableOffersViewModel by viewModels()
    private lateinit var binding: AvailableOrdersFragmentBinding
    private val adapter = AvailableRecyclerViewAdapter()

    @Inject
    lateinit var userUtils: UserUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AvailableOrdersFragmentBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllAvailablePackages().observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.pbAvailableItems.visibility = View.INVISIBLE
                    binding.tvNoAvailableItems.visibility = View.GONE
                    if(it.data!!.isEmpty()){
                        binding.tvNoAvailableItems.visibility = View.VISIBLE
                    }
                    val size = userUtils.getUser()!!.size ?: "HUGE"
                    adapter.setItems(it.data.filter { data -> if(data.size != null) sizeSorter(size, data.size!!) else true })
                }
                Status.ERROR -> {
                    binding.pbAvailableItems.visibility = View.INVISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.pbAvailableItems.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setUpRecyclerView(){
        binding.recyclerView.adapter = adapter
        adapter.clickListener = this
    }

    override fun onImageButtonClicked(pack: Package) {
        val bundle = Bundle()
        Log.d("offer", "Package selected: $pack")
        bundle.putString("PACKAGE_ID", pack.id)
        bundle.putString("PACKAGE_PRICE", pack.price.toString())
        Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
            .navigate(R.id.on_dialog_opened, bundle)
    }
}