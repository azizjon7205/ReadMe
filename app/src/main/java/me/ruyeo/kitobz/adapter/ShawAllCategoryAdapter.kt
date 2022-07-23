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
import me.ruyeo.kitobz.databinding.ItemAllCategoryBinding
import me.ruyeo.kitobz.model.Category

class ShawAllCategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    var onClick: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemAllCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemAllCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val category = dif.currentList[adapterPosition]
            with(binding) {
                tvCategName.text = category.name ?: "All books"
                tvCategCount.text = category.count.toString()

                Glide.with(itemView)
                    .load(category.image)
//                    .override(100, 100)
                    .error(R.drawable.ic_book)
                    .into(ivCategory)

                root.setOnClickListener{
                    onClick?.invoke(category)
                }
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