package hu.bme.aut.movesy.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.movesy.databinding.PackageRowBinding
import hu.bme.aut.movesy.model.Package

//class OrderRecyclerViewAdapter: ListAdapter<Package, OrderRecyclerViewAdapter.PackageViewHolder>(itemCallback){

   //companion object{
   //    object itemCallback : DiffUtil.ItemCallback<Package>(){
   //        override fun areItemsTheSame(oldItem: Package, newItem: Package): Boolean {
   //            return oldItem.id == newItem.id
   //        }

   //        override fun areContentsTheSame(oldItem: Package, newItem: Package): Boolean {
   //            return oldItem == newItem
   //        }
   //    }
   //}

   //inner class PackageViewHolder(val binding: PackageRowBinding) : RecyclerView.ViewHolder(binding.root){
   //    lateinit var currentPackage: Package



   //    init {

   //    }
   //}

   //override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {

   //}

   //override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {

   //}
//}