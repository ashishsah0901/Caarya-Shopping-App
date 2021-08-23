package com.example.caaryainternshiptask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.caaryainternshiptask.databinding.ListItemBinding
import com.example.caaryainternshiptask.db.ShoppingItem

class ItemAdapter(
        private val listener: OnItemClickListener,
        private val deleteListener: OnItemClickForDelete
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            productNameTV.text = differ.currentList[position].name
            descriptionTV.text = differ.currentList[position].description
            val storePrice = "\u20B9${differ.currentList[position].storePrice}"
            priceStoreTV.text = storePrice
            val marketPrice = "\u20B9${differ.currentList[position].marketPrice}"
            priceMarketTV.text = marketPrice
            if(differ.currentList[position].image != null) {
                itemIV.setImageBitmap(differ.currentList[position].image)
            }
            editBT.setOnClickListener {
                listener.onClick(differ.currentList[position])
            }
            deleteBT.setOnClickListener {
                deleteListener.onDelete(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener{
        fun onClick(item: ShoppingItem)
    }

    interface OnItemClickForDelete{
        fun onDelete(item: ShoppingItem)
    }

}