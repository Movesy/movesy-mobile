package hu.bme.aut.movesy.adapter

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.aut.movesy.R
import hu.bme.aut.movesy.databinding.PackageRowBinding
import hu.bme.aut.movesy.model.Package
import hu.bme.aut.movesy.viewmodel.Status
import kotlinx.coroutines.currentCoroutineContext

class OrderRecyclerViewAdapter: RecyclerView.Adapter<OrderRecyclerViewAdapter.PackageViewHolder>(){

    private val items = mutableListOf<Package>()
    private lateinit var parentContext: Context

    fun setItems(items: List<Package>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(
        holder: PackageViewHolder,
        position: Int,
    ) {
        holder.currentPackage = items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder{
        val binding: PackageRowBinding = PackageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return PackageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    class PackageViewHolder(val binding: PackageRowBinding) : RecyclerView.ViewHolder(binding.root){
       lateinit var currentPackage: Package

       init {

       }
   }
}