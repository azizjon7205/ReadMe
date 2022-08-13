package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import me.ruyeo.kitobz.databinding.ItemAudioListBinding
import me.ruyeo.kitobz.model.AudioItem
import me.ruyeo.kitobz.model.Popup

class AudioListAdapter : ListAdapter<AudioItem, AudioListAdapter.VH>(DiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemAudioListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<AudioItem>() {
        override fun areItemsTheSame(oldItem: AudioItem, newItem: AudioItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AudioItem, newItem: AudioItem): Boolean {
            return oldItem.equals(newItem)
        }

    }

    inner class VH(var itemAudioListBinding: ItemAudioListBinding) :
        RecyclerView.ViewHolder(itemAudioListBinding.root) {
        fun bind(audioItem: AudioItem) {
            itemAudioListBinding.apply {
                tvNumber.text = audioItem.id.toString()
                tvAudioName.text = audioItem.name
                tvTotalTime.text = audioItem.totalTime

                if (audioItem.isPlaying) {
                    icPlay.visibility = View.VISIBLE
                } else {
                    icPlay.visibility = View.GONE
                }

            }
        }

    }


}