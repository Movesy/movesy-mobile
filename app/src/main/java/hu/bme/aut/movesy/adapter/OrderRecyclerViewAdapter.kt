package hu.bme.aut.movesy.adapter

import android.annotation.SuppressLint
import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.PackagePastRowExtendedBinding
import hu.bme.aut.movesy.databinding.PackageRowExtendedBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.model.User
import hu.bme.aut.movesy.utils.PackageStatus
import hu.bme.aut.movesy.utils.Status
import hu.bme.aut.movesy.utils.UserUtils
import hu.bme.aut.movesy.utils.convertToSimpleDateFormat
import javax.inject.Inject


class OrderRecyclerViewAdapter (val user: User): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val items = mutableListOf<Package>()
    private lateinit var parentContext: Context
    lateinit var clickListener: onOfferClickListener

    fun setItems(items: List<Package>){
        var differentItems = this.items
        differentItems.removeAll(items)
        this.items.removeAll(differentItems)
        var mutableItems = items.toMutableList()
        mutableItems.removeAll(this.items)
        this.items.addAll(mutableItems)
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
            holder.binding.tvExtendedDate.text = currentPackage.creationDate?.let { convertToSimpleDateFormat(it)}
            holder.binding.tvExtendedDeadline.text =
                parentContext.getString(R.string.package_deadline,
                    currentPackage.deadline?.let { convertToSimpleDateFormat(it) })
            holder.binding.tvExtendedFrom.text =
                parentContext.getString(R.string.package_from, currentPackage.from?.address)
            holder.binding.tvExtendedPrice.text =
                parentContext.getString(R.string.package_price, currentPackage.price)
            holder.binding.tvExtendedSize.text =
                parentContext.getString(R.string.package_size, currentPackage.size)
            holder.binding.tvExtendedStatus.text =
                parentContext.getString(R.string.package_status, currentPackage.status?.displayName())
            holder.binding.tvExtendedTo.text =
                parentContext.getString(R.string.package_to, currentPackage.to?.address)
            holder.binding.tvExtendedTransporter.text =
                if(user.role == "USER"){
                    parentContext.getString(R.string.package_transporter, currentPackage.transporterName)
                } else {
                    parentContext.getString(R.string.customer_name, currentPackage.username)
                }
            holder.bindOnClickListener()
        }
        else if (holder is PackagePastViewHolder){
            holder.currentPackage = items[position]
            val currentPackage = holder.currentPackage
            holder.binding.tvPackageName.text =
                parentContext.getString(R.string.package_name, currentPackage.name)
            holder.binding.tvExtendedDate.text = currentPackage.creationDate?.let { convertToSimpleDateFormat(it)}
            holder.binding.tvExtendedDeadline.text =
                parentContext.getString(R.string.package_deadline,
                    currentPackage.deadline?.let { convertToSimpleDateFormat(it) })
            holder.binding.tvExtendedFrom.text =
                parentContext.getString(R.string.package_from, currentPackage.from?.address)
            holder.binding.tvExtendedPrice.text =
                parentContext.getString(R.string.package_price, currentPackage.price)
            holder.binding.tvExtendedSize.text =
                parentContext.getString(R.string.package_size, currentPackage.size)
            holder.binding.tvExtendedTo.text =
                parentContext.getString(R.string.package_to, currentPackage.to?.address)
            holder.binding.tvExtendedTransporter.text =
                if(user.role == "USER"){
                    parentContext.getString(R.string.package_transporter, currentPackage.transporterName)
                } else {
                    parentContext.getString(R.string.customer_name, currentPackage.username)
                }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if(item.status == PackageStatus.DELIVERED)
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
            binding.tvExtendedTo.visibility = View.VISIBLE
            binding.tvExtendedFrom.visibility = View.VISIBLE
            binding.tvExtendedDeadline.visibility = View.VISIBLE
            binding.tvExtendedSize.visibility = View.VISIBLE
            if(currentPackage.status != PackageStatus.WAITING_FOR_REVIEW){
                binding.tvExtendedPrice.visibility = View.VISIBLE
                binding.tvExtendedTransporter.visibility = View.VISIBLE
            }
        }

        fun bindOnClickListener(){
            when(currentPackage.status){
                PackageStatus.WAITING_FOR_REVIEW  ->{
                    binding.tvExtendedStatus.setTextColor( ContextCompat.getColor(parentContext,R.color.waiting_for_quote ))
                    binding.moneyImageButton.setOnClickListener {
                        clickListener.onOfferClicked(currentPackage)
                    }
                    binding.moneyImageButton.setImageResource(R.drawable.ic_baseline_attach_money_24)
                }
                PackageStatus.SENT  ->{
                    binding.tvExtendedStatus.setTextColor( ContextCompat.getColor(parentContext,R.color.received ))
                    if(user.role == "TRANSPORTER"){
                        binding.moneyImageButton.setOnClickListener {
                            clickListener.onTransferClicked(currentPackage)
                        }
                        binding.moneyImageButton.setImageResource(R.drawable.ic_baseline_delivery_dining_24)
                    } else {
                        binding.moneyImageButton.setImageResource(R.drawable.ic_baseline_attach_money_24)
                        binding.moneyImageButton.setOnClickListener {
                            clickListener.onOfferClicked(currentPackage)
                        }
                    }
                }
                PackageStatus.IN_TRANSIT  ->{
                    binding.tvExtendedStatus.setTextColor(ContextCompat.getColor(parentContext, R.color.status_in_transit))
                    if(user.role == "TRANSPORTER"){
                        binding.moneyImageButton.setOnClickListener {
                            clickListener.onDeliveredClicked(currentPackage)
                        }
                        binding.moneyImageButton.setImageResource(R.drawable.ic_baseline_compare_arrows_24)
                    } else {
                        binding.moneyImageButton.visibility = View.GONE
                    }
                }
            }
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
        fun onTransferClicked(packageID: Package)
        fun onDeliveredClicked(packageID: Package)
        fun onReviewClicked(packageID: Package)
    }

    fun updatePackage( pack: Package){
        val originalPackage = items.find { p -> p.id == pack.id }
        val index = items.indexOf(originalPackage)
        items.removeAt(index)
        items.add(index, pack)
        notifyItemChanged(index)
    }

    fun getItemByPosition(index: Int) = items[index]

    fun deletePackage(pack: Package){
        val index = items.indexOf(pack)
        items.remove(pack)
        notifyItemRemoved(index)
    }

    fun addPackageToPosition(pack: Package, index: Int){
        items.add(index, pack)
        notifyItemInserted(index)
    }
}