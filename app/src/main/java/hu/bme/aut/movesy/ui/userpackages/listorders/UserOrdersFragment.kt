package hu.bme.aut.movesy.ui.userpackages.listorders

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.adapter.OrderRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.OrderListFragmentBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.ui.userpackages.neworder.NewOrderFragment
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
        setUpTouchHelper()
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
        if(userUtils.getUser()!!.role == "TRANSPORTER"){
            binding.btNewOrder.text = "Browse Orders"
            binding.btNewOrder.setOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
                    .navigate(R.id.on_browse_order_selected_global_action)
            }
        } else {
            binding.btNewOrder.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("MODE", "CREATE")
                Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
                    .navigate(R.id.on_new_order_selected_global_action, bundle)
            }
        }

    }


    private fun setupObservers(){
        viewModel.getPackageOfUser().observe(viewLifecycleOwner, {
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
        adapter = OrderRecyclerViewAdapter(userUtils.getUser()!!)
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

    override fun onTransferClicked(pack: Package) {
        showAlertDialog(pack, PackageStatus.IN_TRANSIT)
    }

    override fun onDeliveredClicked(pack: Package) {
        showAlertDialog(pack, PackageStatus.DELIVERED)
    }

    override fun onReviewClicked(pack: Package) {
        val bundle = Bundle()
        bundle.putString("PACKAGE_ID", pack.id)
        bundle.putString("PACKAGE_NAME", pack.name)
        bundle.putString("TRANSPORTER_ID", pack.transporterID)
        bundle.putString("CREATION_DATE", pack.creationDate)
        bundle.putString("TRANSPORTER_NAME", pack.transporterName)
        bundle.putString("USER_NAME", pack.username)
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

    private fun showAlertDialog(pack: Package, status: PackageStatus){
        var question = if(status == PackageStatus.IN_TRANSIT) "deliver this package?" else "complete this order?"
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Are you sure you want to $question")
        builder.setPositiveButton("Yes"){_, _ ->
            pack.status = status
            updatePackage(pack)
        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun updatePackage(pack: Package){
        viewModel.editPackage(pack).observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {binding.orderListPb.visibility = View.VISIBLE}
                Status.ERROR -> {
                    binding.orderListPb.visibility = View.GONE
                    Toast.makeText(context, "Failed to update package!", Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    adapter.updatePackage(pack)
                    setRecyclerViewData()
                    binding.orderListPb.visibility = View.GONE
                    if(adapter.itemCount == 0){
                        binding.btNewOrder.visibility = View.VISIBLE
                        binding.tvNoItems.visibility = View.VISIBLE
                    }
                    Snackbar.make(this.requireView(), "Package Updated", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpTouchHelper(){
        var th = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var index = viewHolder.adapterPosition
                val pack = adapter.getItemByPosition(index)
                when(direction){
                    ItemTouchHelper.LEFT ->{ onItemDeletedWithSwipe(pack, index) }
                    ItemTouchHelper.RIGHT ->{ onItemEditedWithSwipe(pack, index) }
                }
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                var index = viewHolder.adapterPosition
                val pack = adapter.getItemByPosition(index)
                if(pack.status == PackageStatus.IN_TRANSIT || pack.status == PackageStatus.DELIVERED) return 0
                if(pack.userID != userUtils.getUser()!!.id) return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }
        val itemTouchHelper = ItemTouchHelper(th)
        itemTouchHelper.attachToRecyclerView(binding.orderList)
    }

    private fun onItemDeletedWithSwipe(pack: Package, index: Int){
        adapter.deletePackage(pack)
        Snackbar.make(this.requireView(), "Package deleted", Snackbar.LENGTH_LONG)
            .addCallback(SnackbarCallback(pack))
            .setAction("Undo"){
                adapter.addPackageToPosition(pack, index)
            }
            .show()
    }

    private fun onItemEditedWithSwipe(pack: Package, index: Int){
        val bundle = Bundle()
        bundle.putString("MODE", "EDIT")
        NewOrderFragment.packageToEdit = pack
        findNavController().navigate(R.id.on_new_order_selected_global_action, bundle)
    }

    inner class SnackbarCallback(val pack: Package): Snackbar.Callback(){
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            if(DISMISS_EVENT_ACTION != event){
                if(userUtils.getUser()!!.role == "TRANSPORTER"){
                    pack.transporterID = null
                    pack.price = null
                    pack.status = PackageStatus.WAITING_FOR_REVIEW
                    Log.d("debug", pack.toString())
                    viewModel.editPackage(pack).observe(viewLifecycleOwner){
                        Log.d("debug", "${it.status} ${it.message}")
                    }
                } else {
                    viewModel.deletePackage(pack).observe(viewLifecycleOwner){
                        Log.d("debug", "${it.status} ${it.message}")
                    }
                }
            }
        }
    }
}