package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemNewArrivalsBinding
import me.ruyeo.kitobz.model.ElectronicBook

class NewArrivalsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var onClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewArrivalsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemNewArrivalsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val banner = dif.currentList[adapterPosition]
            with(binding) {

                root.setOnClickListener {
                    onClick?.invoke()
                }
            }
        }
    }

    fun submitList(list: List<ElectronicBook>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ElectronicBook>() {
            override fun areItemsTheSame(oldItem: ElectronicBook, newItem: ElectronicBook): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ElectronicBook, newItem: ElectronicBook): Boolean =
                oldItem == newItem
        }
    }


}