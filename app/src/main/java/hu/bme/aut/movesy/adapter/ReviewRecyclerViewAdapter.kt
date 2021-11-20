package hu.bme.aut.movesy.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.movesy.databinding.TransporterReviewRowBinding
import hu.bme.aut.movesy.model.Review

class ReviewRecyclerViewAdapter: RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>(){

    private val items = mutableListOf<Review>()

    fun setItems(newItems: List<Review>){
        this.items.clear()
        this.items.addAll(newItems)
        Log.d("status", items.toString())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: ReviewRecyclerViewAdapter.ReviewViewHolder,
        position: Int,
    ) {
        holder.currentReview = items[position]
        val currentReview = holder.currentReview
        holder.binding.reviewBarTransporter.rating = currentReview.rating.toFloat()
        holder.binding.tvReviewCustomerName.text = currentReview.customerUsername
        holder.binding.tvReviewCustomerComment.text = currentReview.description
        holder.binding.tvTransporterReviewDate.text = currentReview.time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRecyclerViewAdapter.ReviewViewHolder {
        val binding: TransporterReviewRowBinding = TransporterReviewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ReviewViewHolder(val binding: TransporterReviewRowBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var currentReview: Review
    }

}