package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.adapter.OrderRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.OrderListFragmentBinding
import hu.bme.aut.movesy.viewmodel.Status

@AndroidEntryPoint
class UserOrdersFragment : Fragment(){

    private val viewModel: UserPackageViewModel by viewModels()
    private lateinit var binding: OrderListFragmentBinding
    private lateinit var adapter: OrderRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderListFragmentBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }


    private fun setupObservers(){
        viewModel.packages.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    var packages = viewModel.packages
                    adapter.setItems(packages.value?.data!!)
                    Log.d("status", "succes ${packages.toString()}")
                }
                Status.ERROR -> {
                    Log.d("status", "error")
                }
                Status.LOADING -> {
                    Log.d("status", "loading")
                }
            }
        })
    }

    private fun setUpRecyclerView(){
        adapter = OrderRecyclerViewAdapter()
        binding.orderList.adapter = adapter
    }
}