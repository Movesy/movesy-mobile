package hu.bme.aut.movesy.ui.userpackages.createreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.databinding.OffersListFragmentBinding
import hu.bme.aut.movesy.databinding.RatingOrderBinding
import hu.bme.aut.movesy.model.Review
import hu.bme.aut.movesy.viewmodel.Status
import hu.bme.aut.movesy.viewmodel.UserUtils
import hu.bme.aut.movesy.viewmodel.getcurrentDateAndTime
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
@AndroidEntryPoint
class CreateReviewFragment : Fragment() {

    private lateinit var binding: RatingOrderBinding

    private val viewModel: CreateReviewViewModel by viewModels()

    @Inject
    lateinit var userUtils: UserUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val packageID = requireArguments()["PACKAGE_ID"] as String
        val packageName = requireArguments()["PACKAGE_NAME"] as String
        val transporterID = requireArguments()["TRANSPORTER_ID"] as String
        val creationDate = requireArguments()["CREATION_DATE"] as String
        binding = RatingOrderBinding.inflate(inflater, container, false)

        var reviewExists: Review? = null

        binding.tvRatingPackageName.text = packageName
        binding.tvRatingDate.text = creationDate

        viewModel.getTransporterName(transporterID).observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                   binding.tvRatingUsername.text =  it.data?.filter {user -> user.id == transporterID }?.first()?.username ?: ""
                }
            }
        }

        viewModel.getRewiew(packageID).observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                    binding.tvRatingUsername.text = it.data?.customerUsername
                    binding.tvRatingDate.text = it.data?.time
                    binding.etmlDescription.setText(it.data?.description ?: "")
                    binding.rtbRating.numStars = it.data?.rating?.toInt() ?: 1
                    reviewExists = it.data
                }
                else -> {
                    //TODO
                }
            }
        }

        binding.btnSubmit.setOnClickListener {

            var review = Review(
                id = "",
                transporterID = transporterID,
                packageID = packageID,
                time = getcurrentDateAndTime(),
                rating = binding.rtbRating.numStars.toDouble(),
                description = binding.etmlDescription.text.toString(),
                customerUsername = userUtils.getUser()!!.id

            )
            if (reviewExists != null){
                review.copy(
                    id = reviewExists!!.id
                )

                viewModel.updateReview(review)
            }else{
                viewModel.saveReview(review)
            }

        }

        return binding.root
    }
}