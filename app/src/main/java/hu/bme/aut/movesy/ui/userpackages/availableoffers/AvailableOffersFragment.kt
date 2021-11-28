package hu.bme.aut.movesy.ui.userpackages.availableoffers

import android.os.Bundle
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
import hu.bme.aut.movesy.utils.Status

@AndroidEntryPoint
class AvailableOffersFragment: Fragment(), AvailableRecyclerViewAdapter.AvailableOrdersClickListener {
    private val viewModel: AvailableOffersViewModel by viewModels()
    private lateinit var binding: AvailableOrdersFragmentBinding
    private val adapter = AvailableRecyclerViewAdapter()

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
                    adapter.setItems(it.data!!)
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
        bundle.putString("PACKAGE_ID", pack.id)
        Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
            .navigate(R.id.on_dialog_opened, bundle)
    }
}