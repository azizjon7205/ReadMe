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
import me.ruyeo.kitobz.databinding.ItemAudioBookBinding
import me.ruyeo.kitobz.model.AudioBook

class AudioBookAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAudioBookBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }


    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemAudioBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val banner = dif.currentList[adapterPosition]
            with(binding) {

            }
        }
    }


    fun submitList(list: List<AudioBook>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<AudioBook>() {
            override fun areItemsTheSame(oldItem: AudioBook, newItem: AudioBook): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AudioBook, newItem: AudioBook): Boolean =
                oldItem == newItem
        }
    }


}