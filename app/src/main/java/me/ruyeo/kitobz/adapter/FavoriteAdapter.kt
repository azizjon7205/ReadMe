package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemEbookBinding

class FavoriteAdapter : ListAdapter<String, FavoriteAdapter.ViewHolder>(ITEM_DIFF) {

    inner class ViewHolder(private val binding: ItemEbookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val item = currentList[position]
            with(binding){
                flEbookIcon.visibility = View.GONE
                ivFavorite.visibility = View.VISIBLE
                tvAudioBookName.text = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEbookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object{
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        }
    }

}