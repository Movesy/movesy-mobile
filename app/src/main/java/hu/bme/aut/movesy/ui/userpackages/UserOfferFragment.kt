package hu.bme.aut.movesy.ui.userpackages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.adapter.OfferRecyclerViewAdapter
import hu.bme.aut.movesy.adapter.OrderRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.OffersListFragmentBinding
import hu.bme.aut.movesy.repository.Repository
import hu.bme.aut.movesy.viewmodel.Status
import javax.inject.Inject

@AndroidEntryPoint
class UserOfferFragment : Fragment(){

    private lateinit var viewModel: UserOfferViewModel
    @Inject
    lateinit var repo: Repository
    private lateinit var binding:OffersListFragmentBinding
    private lateinit var adapter: OfferRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OffersListFragmentBinding.inflate(inflater, container, false)
        TODO("send down the package ID")
        viewModel = UserOfferViewModel(repo, savedInstanceState!!["PACKAGE_ID"]!!.toString())
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }


    private fun setupObservers(){
        viewModel.offers.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {

                    adapter.setItems(viewModel.offerInfos!!)

                }
                Status.ERROR -> {
                    Log.d("status", "error")
                }
                Status.LOADING -> {
                    Log.d("status", "loading")
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        adapter = OfferRecyclerViewAdapter()
        binding.offerList.adapter = adapter
    }
}