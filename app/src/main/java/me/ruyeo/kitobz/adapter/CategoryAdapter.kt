package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.databinding.ItemCategoryBinding
import me.ruyeo.kitobz.databinding.ItemCategoryHeaderBinding
import me.ruyeo.kitobz.model.Category

class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        Log.d("@@@", "Adapter done")
    }

    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    private val ITEM_HEADER = 0
    private val ITEM_VIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == ITEM_HEADER) {
            val bindingHeader = ItemCategoryHeaderBinding.inflate(inflater, parent, false)
            return HeaderViewHolder(bindingHeader)
        }
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ITEM_HEADER
        }
        return ITEM_VIEW
    }

    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
//            Log.d("@@@", "Adapter ${dif.currentList.size}")
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = dif.currentList[adapterPosition]
//            Log.d("@@@", "Item ${d.name}")
            with(binding) {
                tvCatsName.text = d.name ?: "All books"
                Glide.with(itemView)
                    .load(d.image)
                    .override(100, 100)
                    .into(ivCats)
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemCategoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = dif.currentList[adapterPosition]
            with(binding) {

            }
        }
    }

    fun submitList(list: List<Category>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem == newItem
        }
    }


}