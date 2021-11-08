package hu.bme.aut.movesy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.OfferRowBinding
import hu.bme.aut.movesy.model.Offer
import hu.bme.aut.movesy.model.OfferInfo


class OfferRecyclerViewAdapter : RecyclerView.Adapter<OfferRecyclerViewAdapter.OfferViewHolder>() {

    private val items = mutableListOf<OfferInfo>()
    private lateinit var parentContext: Context

    fun setItems(items: List<OfferInfo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(
        holder: OfferViewHolder,
        position: Int,
    ) {
        holder.currentOffer = items[position]
        val currentOffer = holder.currentOffer
        holder.binding.tvReviewPrice.text = currentOffer.price.toString()
        holder.binding.tvOfferUsername.text = parentContext.getString(R.string.loading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding: OfferRowBinding =
            OfferRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return OfferViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    class OfferViewHolder(val binding: OfferRowBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var currentOffer: OfferInfo

        init {

        }
    }
}