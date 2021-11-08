package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.ListViewContainerBinding

@AndroidEntryPoint
class UserPackageFragment: Fragment() {

    private lateinit var binding: ListViewContainerBinding

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

        binding.bubbleTabBar.addBubbleListener{
            when(it){
                R.id.miMyOrders -> navigate(R.id.on_order_selected_global_action)
                R.id.miNewOrder -> navigate(R.id.on_new_order_selected_global_action)
                R.id.miProfile ->  navigate(R.id.on_profile_selected_global_action)
            }
        }
    }

    private fun navigate(viewid: Int){
        Navigation.findNavController(requireActivity(),R.id.nav_orders_fragment_container)
            .navigate(viewid)
    }
}