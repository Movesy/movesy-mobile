package hu.bme.aut.movesy.ui.userpackages.offers

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
import hu.bme.aut.movesy.adapter.OfferRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.OffersListFragmentBinding
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.OfferInfo
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.utils.Status
import javax.inject.Inject

@AndroidEntryPoint
class UserOfferFragment : Fragment(), OfferRecyclerViewAdapter.OfferActionsClickListener{

    @Inject
    lateinit var repo: Repository

    private val viewModel: UserOfferViewModel by viewModels()

    private lateinit var binding: OffersListFragmentBinding
    private lateinit var adapter: OfferRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OffersListFragmentBinding.inflate(inflater, container, false)
        savedInstanceState?.let { Log.d("error", savedInstanceState.toString())}
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val packageId = requireArguments()["PACKAGE_ID"].toString()
        val packageName = requireArguments()["PACKAGE_NAME"].toString()
        binding.tvOrderName.text = packageName
        setupObservers(packageId)
    }


    private fun setupObservers(packageId: String){
        binding.pbUserOffers.visibility = View.VISIBLE
        viewModel.getOffers(packageId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.setItems(it.data!!)
                    if(adapter.itemCount != 0){
                        binding.pbUserOffers.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding.pbUserOffers.visibility = View.GONE
                    if(it.message != null && it.message.contains("204")){
                        binding.tvNoOffers.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                Status.LOADING -> {
                    binding.pbUserOffers.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        adapter = OfferRecyclerViewAdapter()
        binding.offerList.adapter = adapter
        adapter.clickListener = this
    }

    override fun onOfferClicked(offer: OfferInfo) {
        val bundle = Bundle()
        bundle.putString("TRANSPORTER_NAME", offer.transporterName)
        bundle.putString("TRANSPORTER_ID", offer.transporterID)
        Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
            .navigate(R.id.on_transporter_selected_action, bundle)
    }

    override fun onOfferAccepted(offer: OfferInfo) {
        binding.pbUserOffers.visibility = View.VISIBLE
        val validOffer = Offer(id = offer.id, transporterID = offer.transporterID, price = offer.price, packageID = offer.packageID)
        viewModel.acceptOffer(validOffer).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("status", "offers accepted")
                    binding.pbUserOffers.visibility = View.GONE
                    Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container)
                        .navigate(R.id.on_order_selected_global_action)
                }
                Status.ERROR -> {
                    binding.pbUserOffers.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.pbUserOffers.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onOfferRejected(offer: OfferInfo) {
        binding.pbUserOffers.visibility = View.VISIBLE
        viewModel.deleteOffer(offer.id).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("status", "offers accepted")
                    binding.pbUserOffers.visibility = View.GONE
                    adapter.deleteItem(offer)
                    if(adapter.itemCount == 0){
                        binding.tvNoOffers.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    binding.pbUserOffers.visibility = View.GONE
                    if(it.message == null || !it.message.contains("204")) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                Status.LOADING -> {
                    binding.pbUserOffers.visibility = View.VISIBLE
                }
            }
        })
    }
}