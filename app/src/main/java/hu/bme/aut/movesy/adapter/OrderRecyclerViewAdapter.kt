package hu.bme.aut.movesy.adapter

import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.FtsOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.PackageRowBinding
import hu.bme.aut.movesy.databinding.PackageRowExtendedBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.viewmodel.Status
import kotlinx.coroutines.currentCoroutineContext

class OrderRecyclerViewAdapter: RecyclerView.Adapter<OrderRecyclerViewAdapter.PackageViewHolder>(){

    private val items = mutableListOf<Package>()
    private lateinit var parentContext: Context

    fun setItems(items: List<Package>){
        this.items.clear()
        this.items.addAll(items)
        Log.d("status", items.toString())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: PackageViewHolder,
        position: Int,
    ) {
        holder.currentPackage = items[position]
        val currentPackage = holder.currentPackage
        holder.binding.tvPackageName.text = parentContext.getString(R.string.package_name, currentPackage.name)
        holder.binding.tvExtendedDate.text = currentPackage.date
        holder.binding.tvExtendedDeadline.text = parentContext.getString(R.string.package_deadline, currentPackage.deadline)
        holder.binding.tvExtendedFrom.text = parentContext.getString(R.string.package_from, currentPackage.from)
        holder.binding.tvExtendedPrice.text = parentContext.getString(R.string.package_price, currentPackage.price)
        holder.binding.tvExtendedSize.text = parentContext.getString(R.string.package_size, currentPackage.size)
        holder.binding.tvExtendedStatus.text = parentContext.getString(R.string.package_status, currentPackage.status)
        holder.binding.tvExtendedTo.text = parentContext.getString(R.string.package_to, currentPackage.to)
        holder.binding.tvExtendedTransporter.text = parentContext.getString(R.string.package_transporter, currentPackage.transporterID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder{
        val binding: PackageRowExtendedBinding = PackageRowExtendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return PackageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    class PackageViewHolder(val binding: PackageRowExtendedBinding) : RecyclerView.ViewHolder(binding.root){
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
            binding.tvExtendedTransporter.visibility = View.VISIBLE
            binding.tvExtendedTo.visibility = View.VISIBLE
            binding.tvExtendedFrom.visibility = View.VISIBLE
            binding.tvExtendedPrice.visibility = View.VISIBLE
            binding.tvExtendedDeadline.visibility = View.VISIBLE
            binding.tvExtendedSize.visibility = View.VISIBLE
        }
   }
}