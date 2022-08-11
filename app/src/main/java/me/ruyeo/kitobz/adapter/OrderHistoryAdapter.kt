package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemOrderHistoryBinding
import me.ruyeo.kitobz.ui.profile.orderhistory.OHistoryModel

class OrderHistoryAdapter : ListAdapter<OHistoryModel, OrderHistoryAdapter.ViewHolder>(ITEM_DIFF) {

    inner class ViewHolder(private val binding: ItemOrderHistoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val item = currentList[position]
            binding.tvOrderNumber.text = item.number.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return if (currentList.size > 3) 3 else currentList.size
    }

    companion object{
        val ITEM_DIFF = object : DiffUtil.ItemCallback<OHistoryModel>(){
            override fun areItemsTheSame(oldItem: OHistoryModel, newItem: OHistoryModel): Boolean {
                return oldItem.number == newItem.number
                        && oldItem.books == newItem.books
            }

            override fun areContentsTheSame(oldItem: OHistoryModel, newItem: OHistoryModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}
