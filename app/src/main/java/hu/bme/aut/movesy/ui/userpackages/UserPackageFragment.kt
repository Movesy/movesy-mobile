package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.ListViewContainerBinding
import hu.bme.aut.movesy.utils.UserUtils
import javax.inject.Inject


@AndroidEntryPoint
class UserPackageFragment: Fragment() {
    private lateinit var binding: ListViewContainerBinding
    @Inject
    lateinit var userUtils:UserUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ListViewContainerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(userUtils.getUser()!!.role == "TRANSPORTER"){
            binding.bubbleTabBar.visibility = View.GONE
            binding.bubbleTabBarTransporter.visibility = View.VISIBLE
        }

        binding.tvReviewCustomerName.text = "MY ORDERS"
        binding.bubbleTabBar.addBubbleListener{
            when(it){
                R.id.miMyOrders -> {
                    navigate(R.id.on_order_selected_global_action)
                    binding.tvReviewCustomerName.text = "MY ORDERS"
                }
                R.id.miNewOrder -> {
                    navigate(R.id.on_new_order_selected_global_action)
                    binding.tvReviewCustomerName.text = "NEW ORDER"
                }
                R.id.miProfile -> {
                    navigate(R.id.on_profile_selected_global_action)
                    binding.tvReviewCustomerName.text = "PROFILE"
                }
            }
        }

        binding.bubbleTabBarTransporter.addBubbleListener{
            when(it){
                R.id.miTransporterMyOrders -> {
                    navigate(R.id.on_order_selected_global_action)
                    binding.tvReviewCustomerName.text = "MY ORDERS"
                }
                R.id.miTransporterBrowse -> {
                    navigate(R.id.on_browse_order_selected_global_action)
                    binding.tvReviewCustomerName.text = "BROWSE"
                }
                R.id.miTransporterProfile -> {
                    navigate(R.id.on_profile_selected_global_action)
                    binding.tvReviewCustomerName.text = "PROFILE"
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d("MENU",menu.toString() )

    }

    private fun navigate(viewid: Int){
        Navigation.findNavController(requireActivity(),R.id.nav_orders_fragment_container)
            .navigate(viewid)
    }
}