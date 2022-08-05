package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemAuthorsBinding
import me.ruyeo.kitobz.model.Banner1

class AuthorsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    var onClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAuthorsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }


    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemAuthorsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val banner = dif.currentList[adapterPosition]
            with(binding) {
                tvAuthorName.text = banner.name

                root.setOnClickListener{
                    onClick?.invoke(banner.name!!)
                }
            }
        }
    }


    fun submitList(list: List<Banner1>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Banner1>() {
            override fun areItemsTheSame(oldItem: Banner1, newItem: Banner1): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Banner1, newItem: Banner1): Boolean =
                oldItem == newItem
        }
    }


}