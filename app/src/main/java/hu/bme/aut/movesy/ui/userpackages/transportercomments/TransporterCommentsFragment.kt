package hu.bme.aut.movesy.ui.userpackages.transportercomments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.adapter.ReviewRecyclerViewAdapter
import hu.bme.aut.movesy.databinding.TransporterReviewBinding
import hu.bme.aut.movesy.utils.Status

@AndroidEntryPoint
class TransporterCommentsFragment : Fragment() {

    private lateinit var binding: TransporterReviewBinding
    private val adapter = ReviewRecyclerViewAdapter()

    private val viewModel: TransporterCommentsViewModel by viewModels()

    private lateinit var transporterName: String
    private lateinit var transporterID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TransporterReviewBinding.inflate(inflater, container, false)
        transporterName  =  requireArguments()["TRANSPORTER_NAME"].toString()
        transporterID  = requireArguments()["TRANSPORTER_ID"].toString()
        binding.tvTransporterUsername.text = transporterName
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCommentsOnTransporter(transporterID).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("debug", it.data.toString())
                    binding.pbTransporterReview.visibility = View.GONE
                    if(it.data!!.isEmpty()) binding.ratingBar3.rating = 0.0.toFloat()
                    else binding.ratingBar3.rating = (it.data.sumOf {review ->  review.rating }/it.data.size).toFloat()
                    adapter.setItems(it.data)
                }
                Status.ERROR -> {
                    binding.pbTransporterReview.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.pbTransporterReview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        binding.recyclerView2.adapter = adapter
    }
}