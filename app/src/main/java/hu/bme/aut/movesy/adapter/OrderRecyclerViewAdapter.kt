package hu.bme.aut.movesy.adapter

import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.PackagePastRowExtendedBinding
import hu.bme.aut.movesy.databinding.PackageRowExtendedBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.viewmodel.Status
import kotlinx.coroutines.currentCoroutineContext


class OrderRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val items = mutableListOf<Package>()
    private lateinit var parentContext: Context
    lateinit var clickListener: onOfferClickListener



    fun setItems(items: List<Package>){
        this.items.clear()
        this.items.addAll(items)

        Log.d("status", items.toString())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if(holder is PackageViewHolder) {
            holder.currentPackage = items[position]
            val currentPackage = holder.currentPackage
            holder.binding.tvPackageName.text =
                parentContext.getString(R.string.package_name, currentPackage.name)
            holder.binding.tvExtendedDate.text = currentPackage.creationDate
            holder.binding.tvExtendedDeadline.text =
                parentContext.getString(R.string.package_deadline, currentPackage.deadline)
            holder.binding.tvExtendedFrom.text =
                parentContext.getString(R.string.package_from, currentPackage.from)
            holder.binding.tvExtendedPrice.text =
                parentContext.getString(R.string.package_price, currentPackage.price)
            holder.binding.tvExtendedSize.text =
                parentContext.getString(R.string.package_size, currentPackage.size)
            holder.binding.tvExtendedStatus.text =
                parentContext.getString(R.string.package_status, currentPackage.status)
            holder.binding.tvExtendedTo.text =
                parentContext.getString(R.string.package_to, currentPackage.to)
            holder.binding.tvExtendedTransporter.text =
                parentContext.getString(R.string.package_transporter, currentPackage.transporterID)
        }
        else if (holder is PackagePastViewHolder){
            holder.currentPackage = items[position]
            val currentPackage = holder.currentPackage
            holder.binding.tvPackageName.text =
                parentContext.getString(R.string.package_name, currentPackage.name)
            holder.binding.tvExtendedDate.text = currentPackage.creationDate
            holder.binding.tvExtendedDeadline.text =
                parentContext.getString(R.string.package_deadline, currentPackage.deadline)
            holder.binding.tvExtendedFrom.text =
                parentContext.getString(R.string.package_from, currentPackage.from)
            holder.binding.tvExtendedPrice.text =
                parentContext.getString(R.string.package_price, currentPackage.price)
            holder.binding.tvExtendedSize.text =
                parentContext.getString(R.string.package_size, currentPackage.size)
            holder.binding.tvExtendedTo.text =
                parentContext.getString(R.string.package_to, currentPackage.to)
            holder.binding.tvExtendedTransporter.text =
                parentContext.getString(R.string.package_transporter, currentPackage.transporterID)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if(item.status == "Delivered")
            return 2
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return if(viewType == 1){
            val binding: PackageRowExtendedBinding = PackageRowExtendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            parentContext = parent.context
            PackageViewHolder(binding)
        } else {
            val binding: PackagePastRowExtendedBinding = PackagePastRowExtendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            parentContext = parent.context
            PackagePastViewHolder(binding)
        }

    }

    override fun getItemCount(): Int = items.size

    inner class PackageViewHolder(val binding: PackageRowExtendedBinding) : RecyclerView.ViewHolder(binding.root){
       lateinit var currentPackage: Package
       private var expanded = false

       init {
            itemView.setOnClickListener {
                if (expanded) hideElements() else showElements()
                expanded = !expanded
            }
           binding.moneyImageButton.setOnClickListener {
               clickListener.onOfferClicked(currentPackage)
           }
           hideElements()
       }

        private fun hideElements() {
            binding.tvExtendedDate.visibility = View.GONE
            binding.tvExtendedTransporter.visibility = View.GONE
            binding.tvExtendedTo.visibility = View.GONE
            binding.tvExtendedFrom.visibility = View.GONE
            binding.tvExtendedPrice.visibility = View.GONE
            binding.tvExtendedDeadline.visibility = View.GONE
            binding.tvExtendedSize.visibility = View.GONE
        }

        private fun showElements() {
            binding.tvExtendedDate.visibility = View.VISIBLE
            binding.tvExtendedTransporter.visibility = View.VISIBLE
            binding.tvExtendedTo.visibility = View.VISIBLE
            binding.tvExtendedFrom.visibility = View.VISIBLE
            binding.tvExtendedPrice.visibility = View.VISIBLE
            binding.tvExtendedDeadline.visibility = View.VISIBLE
            binding.tvExtendedSize.visibility = View.VISIBLE
        }
   }

    inner class PackagePastViewHolder(val binding: PackagePastRowExtendedBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var currentPackage: Package
        private var expanded = false

        init {
            itemView.setOnClickListener {
                if (expanded) hideElements() else showElements()
                expanded = !expanded
            }
            binding.reviewImageButton.setOnClickListener {
                clickListener.onReviewClicked(currentPackage)
            }
            hideElements()
        }

        private fun hideElements() {
            binding.tvExtendedDate.visibility = View.GONE
            binding.tvExtendedTo.visibility = View.GONE
            binding.tvExtendedFrom.visibility = View.GONE
            binding.tvExtendedPrice.visibility = View.GONE
            binding.tvExtendedDeadline.visibility = View.GONE
            binding.tvExtendedSize.visibility = View.GONE
        }

        private fun showElements() {
            binding.tvExtendedDate.visibility = View.VISIBLE
            binding.tvExtendedTo.visibility = View.VISIBLE
            binding.tvExtendedFrom.visibility = View.VISIBLE
            binding.tvExtendedPrice.visibility = View.VISIBLE
            binding.tvExtendedDeadline.visibility = View.VISIBLE
            binding.tvExtendedSize.visibility = View.VISIBLE
        }
    }

    interface onOfferClickListener {
        fun onOfferClicked(packageID: Package)
        fun onReviewClicked(packageID: Package)
    }

}