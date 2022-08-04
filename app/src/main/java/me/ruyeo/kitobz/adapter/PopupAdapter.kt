package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.databinding.ItemPopupBinding
import me.ruyeo.kitobz.model.Popup

class PopupAdapter : ListAdapter<Popup, PopupAdapter.VH>(DiffUtil()) {


    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Popup>() {
        override fun areItemsTheSame(oldItem: Popup, newItem: Popup): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Popup, newItem: Popup): Boolean {
            return oldItem.equals(newItem)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemPopupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class VH(var itemPopupBinding: ItemPopupBinding) :
        RecyclerView.ViewHolder(itemPopupBinding.root) {
        fun onBind(popup: Popup) {
            itemPopupBinding.apply {
                tvText.text = popup.text
                Glide.with(icImage).load(popup.image).into(icImage)
            }
        }
    }


}