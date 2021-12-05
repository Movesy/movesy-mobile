package hu.bme.aut.movesy.ui.userpackages.createreview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.RatingOrderBinding
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.model.ReviewTransferObject
import hu.bme.aut.movesy.utils.*
import okhttp3.ResponseBody
import javax.inject.Inject

@AndroidEntryPoint
class CreateReviewFragment : Fragment() {

    private lateinit var binding: RatingOrderBinding

    private val viewModel: CreateReviewViewModel by viewModels()

    @Inject
    lateinit var userUtils: UserUtils

    private var mode = MODE_CREATE

    private lateinit var review: Review

    companion object{
        const val MODE_EDIT = 0
        const val MODE_CREATE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val packageID = requireArguments()["PACKAGE_ID"] as String
        val packageName = requireArguments()["PACKAGE_NAME"] as String
        val transporterID = requireArguments()["TRANSPORTER_ID"] as String
        val transporterName = requireArguments()["TRANSPORTER_NAME"] as String
        val userName = requireArguments()["USER_NAME"] as String
        val creationDate = requireArguments()["CREATION_DATE"] as String
        binding = RatingOrderBinding.inflate(inflater, container, false)
        binding.pbReview.visibility = View.VISIBLE

        binding.tvRatingPackageName.text = requireContext().getString(R.string.package_name, packageName)
        binding.tvRatingDate.text = convertToSimpleDateFormat(creationDate)
        binding.tvRatingUsername.text =
            if(userUtils.getUser()!!.role == "USER") {
               requireContext().getString(R.string.package_transporter, transporterName)
            } else {
                requireContext().getString(R.string.customer_name, userName)
            }
        binding.etmlDescription.isEnabled = false
        binding.rtbRating.isEnabled = false

        viewModel.getRewiew(packageID).observe(viewLifecycleOwner){
            Log.d("debug", it.message.toString())
            when(it.status){
                Status.SUCCESS ->{
                    if(userUtils.getUser()?.role != "TRANSPORTER"){
                        binding.etmlDescription.isEnabled = true
                        binding.rtbRating.isEnabled = true
                    }
                    binding.pbReview.visibility = View.GONE
                    if(it.data != null){
                        binding.tvRatingDate.text = convertToSimpleDateFormat(it.data.time)
                        binding.etmlDescription.setText(it.data.description)
                        binding.rtbRating.rating = (it.data.rating.toInt()).toFloat()
                        mode = MODE_EDIT
                        review = it.data
                    } else {
                        mode = MODE_CREATE
                    }
                }
                Status.ERROR -> {
                    binding.pbReview.visibility = View.GONE
                    Toast.makeText(context, "Error while loading review: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> { binding.pbReview.visibility = View.VISIBLE }
            }
        }

        binding.btnSubmit.setOnClickListener {
            if(mode == MODE_EDIT){
                review.rating = binding.rtbRating.rating.toDouble()
                review.time = getcurrentDateAndTime()
                review.rating = binding.rtbRating.rating.toDouble()
                review.description = binding.etmlDescription.text.toString()
                viewModel.updateReview(review).observe(viewLifecycleOwner){
                    handleReviewCompleted(it)
                }

            } else {
                val newReview = ReviewTransferObject(
                    id = null,
                    transporterID = transporterID,
                    packageID = packageID,
                    time = getcurrentDateAndTime(),
                    rating = binding.rtbRating.rating.toDouble(),
                    description = binding.etmlDescription.text.toString(),
                    customerUsername = userUtils.getUser()!!.username
                )
                viewModel.saveReview(newReview).observe(viewLifecycleOwner){
                    handleReviewCompleted(it)
                }
            }

        }

        if(userUtils.getUser()?.role == "TRANSPORTER"){
            binding.btnSubmit.visibility= View.GONE
            binding.rtbRating.isEnabled = false
            binding.etmlDescription.focusable = EditText.NOT_FOCUSABLE
        }

        return binding.root
    }

    private fun <T> handleReviewCompleted(result: Resource<T>){
        when(result.status){
            Status.SUCCESS ->{
                Navigation.findNavController(requireActivity(), R.id.nav_orders_fragment_container).popBackStack()
            }
            Status.ERROR -> {
                binding.pbReview.visibility = View.GONE
                Toast.makeText(context, "Error while loading review: ${result.message}", Toast.LENGTH_SHORT).show()
            }
            Status.LOADING -> { binding.pbReview.visibility = View.VISIBLE }
        }
    }

}