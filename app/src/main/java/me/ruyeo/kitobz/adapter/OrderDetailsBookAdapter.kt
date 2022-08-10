package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemBasketBookBinding
import me.ruyeo.kitobz.ui.basket.information.InfoModel

class OrderDetailsBookAdapter : ListAdapter<InfoModel, OrderDetailsBookAdapter.ViewHolder>(ITEM_DIFF){

    inner class ViewHolder(private val binding: ItemBasketBookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val item = currentList[position]

            binding.llBookCount.visibility = View.GONE
            binding.flDelete.visibility = View.GONE
            binding.tvPriceBookCount.visibility = View.GONE
            binding.tvPrice.text = item.price.toString()

            if (item.bookType == "audio"){
                binding.ivAudio.visibility = View.VISIBLE
                binding.ivElectronicPaper.visibility = View.GONE
            }else{
                binding.ivAudio.visibility = View.GONE
                binding.ivElectronicPaper.visibility = View.VISIBLE
            }

            if (item.paymentCash) binding.tvPaymentType.visibility = View.VISIBLE
            else binding.tvPaymentType.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBasketBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object{
        val ITEM_DIFF = object : DiffUtil.ItemCallback<InfoModel>(){
            override fun areItemsTheSame(oldItem: InfoModel, newItem: InfoModel): Boolean {
                return oldItem.bookType == newItem.bookType
                        && oldItem.paymentCash == newItem.paymentCash
            }

            override fun areContentsTheSame(oldItem: InfoModel, newItem: InfoModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}