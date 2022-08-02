package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemBannerMainBinding
import me.ruyeo.kitobz.model.Banner

class BannerAdapter : ListAdapter<Banner, BannerAdapter.ViewHolder>(ITEM_DIFF){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBannerMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(private val binding: ItemBannerMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val banner = getItem(adapterPosition)
            with(binding) {
                Glide.with(itemView)
                    .load(banner.image)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivBanner)
            }
        }
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Banner>() {
            override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }


}