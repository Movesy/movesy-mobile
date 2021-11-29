package hu.bme.aut.movesy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.AvailablePackageRowBinding
import hu.bme.aut.movesy.databinding.OfferRowBinding
import hu.bme.aut.movesy.model.OfferInfo
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.utils.convertToSimpleDateFormat

class AvailableRecyclerViewAdapter : RecyclerView.Adapter<AvailableRecyclerViewAdapter.AvailablePackageViewHolder>() {

    private val items = mutableListOf<Package>()
    private lateinit var parentContext: Context
    lateinit var clickListener: AvailableOrdersClickListener

    fun setItems(items: List<Package>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(
        holder: AvailableRecyclerViewAdapter.AvailablePackageViewHolder,
        position: Int,
    ) {
        holder.currentPackage = items[position]
        val currentPackage = holder.currentPackage
        holder.binding.tvAvailableUserName.text =  parentContext.getString(R.string.customer_name, currentPackage.username)
        holder.binding.tvPackageName.text =
            parentContext.getString(R.string.package_name, currentPackage.name)
        holder.binding.tvExtendedDate.text = currentPackage.creationDate?.let { convertToSimpleDateFormat(it) }
        holder.binding.tvExtendedDeadline.text =
             parentContext.getString(R.string.package_deadline,
                 currentPackage.deadline?.let { convertToSimpleDateFormat(it) })
        holder.binding.tvExtendedFrom.text =
            parentContext.getString(R.string.package_from, currentPackage.from?.address)
        holder.binding.tvExtendedSize.text =
            parentContext.getString(R.string.package_size, currentPackage.size)
        holder.binding.tvExtendedTo.text =
            parentContext.getString(R.string.package_to, currentPackage.to?.address)
        holder.binding.tvAvailableTransporterName.text = parentContext.getString(R.string.package_transporter, currentPackage.transporterName)
        holder.binding.tvAvailableTransporterName.text = parentContext.getString(R.string.package_price, currentPackage.price)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableRecyclerViewAdapter.AvailablePackageViewHolder {
        val binding: AvailablePackageRowBinding =
            AvailablePackageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return AvailablePackageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class AvailablePackageViewHolder(val binding: AvailablePackageRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var expanded = false
        lateinit var currentPackage: Package

        init {
            binding.reviewImageButton.setOnClickListener {
                clickListener.onImageButtonClicked(currentPackage)
            }
            itemView.setOnClickListener {
                if (expanded) hideElements() else showElements()
                expanded = !expanded
            }
            hideElements()
        }

        private fun hideElements() {
            binding.tvExtendedDate.visibility = View.GONE
            binding.tvExtendedTo.visibility = View.GONE
            binding.tvExtendedFrom.visibility = View.GONE
            binding.tvExtendedDeadline.visibility = View.GONE
            binding.tvExtendedSize.visibility = View.GONE
            binding.tvAvailableTransporterName.visibility = View.GONE
            binding.tvAvailablePrice.visibility = View.GONE
        }
        private fun showElements() {
            binding.tvExtendedDate.visibility = View.VISIBLE
            binding.tvExtendedTo.visibility = View.VISIBLE
            binding.tvExtendedFrom.visibility = View.VISIBLE
            binding.tvExtendedDeadline.visibility = View.VISIBLE
            binding.tvExtendedSize.visibility = View.VISIBLE
            if(!currentPackage.transporterName.isNullOrBlank()){
                binding.tvAvailableTransporterName.visibility = View.VISIBLE
                binding.tvAvailablePrice.visibility = View.VISIBLE
            }
        }

    }

    interface AvailableOrdersClickListener {
        fun onImageButtonClicked(pack: Package)
    }
}
