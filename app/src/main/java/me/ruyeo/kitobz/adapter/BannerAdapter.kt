package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemBannerMainBinding
import me.ruyeo.kitobz.model.Banner

class BannerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBannerMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }


    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemBannerMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val banner = dif.currentList[adapterPosition]
            with(binding) {
                Glide.with(itemView)
                    .load(banner.image)
                    .override(100, 100)
                    .error(R.drawable.ic_book)
                    .into(ivBanner)
            }
        }
    }


    fun submitList(list: List<Banner>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Banner>() {
            override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem == newItem
        }
    }


}