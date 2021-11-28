package hu.bme.aut.movesy.ui.userpackages.listorders

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
import hu.bme.aut.movesy.adapter.OrderRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.OrderListFragmentBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.utils.PackageStatus
import hu.bme.aut.movesy.utils.Status
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject

@AndroidEntryPoint
class UserOrdersFragment : Fragment(), OrderRecyclerViewAdapter.onOfferClickListener {

    private val viewModel: UserPackageViewModel by viewModels()
    private lateinit var binding: OrderListFragmentBinding
    private lateinit var adapter: OrderRecyclerViewAdapter
    private var orders = emptyList<Package>()
    private var currentState = ACTIVE_ORDERS

    @Inject
    lateinit var userUtils: UserUtils

    companion object{
        const val ACTIVE_ORDERS = 0
        const val PAST_ORDERS = 1
    }

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
        binding.btnOrdersInProgress.setOnClickListener {
            binding.btNewOrder.visibility = View.GONE
            binding.tvNoItems.visibility = View.GONE
            currentState = ACTIVE_ORDERS
            setRecyclerViewData()
        }
        binding.btnCompletedOrders.setOnClickListener {
            binding.btNewOrder.visibility = View.GONE
            binding.tvNoItems.visibility = View.GONE
            currentState = PAST_ORDERS
            setRecyclerViewData()
        }
        binding.btNewOrder.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
                .navigate(R.id.on_new_order_selected_global_action)
        }
    }


    private fun setupObservers(){
        viewModel.getPackagesOfUser().observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    orders = it.data!!
                    setRecyclerViewData()
                    binding.orderListPb.visibility = View.INVISIBLE
                    binding.orderList.visibility = View.VISIBLE
                    if(orders.isNotEmpty()) {
                        binding.btNewOrder.visibility = View.GONE
                        binding.tvNoItems.visibility = View.GONE
                    }
                    Log.d("status", "succes ${orders.toString()}")
                }
                Status.ERROR -> {
                    if(it.message?.contains("204") == false){
                        Toast.makeText(context,"Service Unavailable", Toast.LENGTH_SHORT).show()
                        Log.d("status","error: ${it.message}")
                    } else {
                        setRecyclerViewData()
                    }
                }
                Status.LOADING -> {
                    binding.orderListPb.visibility = View.VISIBLE
                    binding.orderList.visibility = View.INVISIBLE
                    Log.d("status", "loading")
                }
            }
        })
    }

    private fun setUpRecyclerView(){
        adapter = OrderRecyclerViewAdapter()
        binding.orderList.adapter = adapter
        adapter.clickListener = this
    }

    override fun onOfferClicked(pack: Package) {
        val bundle = Bundle()
        bundle.putString("PACKAGE_ID", pack.id)
        bundle.putString("PACKAGE_NAME", pack.name)
        Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
            .navigate(R.id.on_package_selected_action, bundle)
    }

    override fun onReviewClicked(pack: Package) {
        val bundle = Bundle()
        bundle.putString("PACKAGE_ID", pack.id)
        bundle.putString("PACKAGE_NAME", pack.name)
        bundle.putString("TRANSPORTER_ID", pack.transporterID)
        bundle.putString("CREATION_DATE", pack.creationDate)
        Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
            .navigate(R.id.on_package_for_review_action, bundle)
    }

    private fun setRecyclerViewData(){
        if(currentState == ACTIVE_ORDERS){
            var activeOrders = orders.filter { it.status != PackageStatus.DELIVERED}
            adapter.setItems(activeOrders)
            if(activeOrders.isEmpty()){
                binding.btNewOrder.visibility = View.VISIBLE
                binding.tvNoItems.visibility = View.VISIBLE
                binding.tvNoItems.text = "You have no current orders"
            }
        } else if(currentState == PAST_ORDERS){
            var pastOrders = orders.filter { it.status == PackageStatus.DELIVERED}
            adapter.setItems(pastOrders)
            if(pastOrders.isEmpty()){
                binding.tvNoItems.visibility = View.VISIBLE
                binding.tvNoItems.text = "You have no past orders"
            }
        }
    }
}