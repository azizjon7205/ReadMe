package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemBasketBookBinding
import me.ruyeo.kitobz.ui.basket.information.InfoModel

class BasketBookAdapter : ListAdapter<InfoModel, BasketBookAdapter.ViewHolder>(ITEM_DIFF){

    var clickDeleteIcon: ((Int) -> Unit)? = null
    var clickAddBCount: ((Int) -> Unit)?= null
    var clickRemoveBCount: ((Int) -> Unit)? = null

    inner class ViewHolder(private val binding:ItemBasketBookBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(position: Int){
            val item = currentList[position]

            binding.tvPrice.text = item.price.toString()
            binding.tvPriceBookCount.text = "x ${item.bookCount}"

            if (item.bookType == "audio"){
                binding.ivAudio.visibility = View.VISIBLE
                binding.ivElectronicPaper.visibility = View.GONE
                binding.llBookCount.visibility = View.GONE
            }else{
                binding.ivAudio.visibility = View.GONE
                binding.ivElectronicPaper.visibility = View.VISIBLE
                if (item.bookType == "paper"){
                    binding.llBookCount.visibility = View.VISIBLE
                    binding.tvPaperBookCount.text = item.bookCount.toString()
                    if (item.bookCount == 1){
                        binding.ivRemove.setBackgroundResource(R.color.gray_light_2)
                        binding.tvPriceBookCount.visibility = View.GONE
                    } else{
                        binding.ivRemove.setBackgroundResource(R.color.blue_light)
                        binding.tvPriceBookCount.visibility = View.VISIBLE
                    }
                }
                else{
                    binding.llBookCount.visibility = View.GONE
                    binding.tvPriceBookCount.visibility = View.GONE
                }
            }

            if (item.paymentCash) binding.tvPaymentType.visibility = View.VISIBLE
            else binding.tvPaymentType.visibility = View.GONE

            binding.flAdd.setOnClickListener {
                clickAddBCount?.invoke(position)
            }
            binding.flRemove.setOnClickListener {
                if (item.bookCount > 1) clickRemoveBCount?.invoke(position)
            }

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