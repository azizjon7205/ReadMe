package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.CategoryItemBinding
import me.ruyeo.kitobz.model.Discuss

class DiscussAdapter : RecyclerView.Adapter<DiscussAdapter.ViewHolder>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = dif.currentList[adapterPosition]
            with(binding) {

            }
        }
    }

    fun submitList(list: List<Discuss>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Discuss>() {
            override fun areItemsTheSame(oldItem: Discuss, newItem: Discuss): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Discuss, newItem: Discuss): Boolean =
                oldItem == newItem
        }
    }

}