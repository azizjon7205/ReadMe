package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemOrderDetailsBinding
import me.ruyeo.kitobz.ui.profile.orderhistory.OHistoryModel

class OrderDetailsAdapter : ListAdapter<OHistoryModel, OrderDetailsAdapter.ViewHolder>(ITEM_DIFF) {

    inner class ViewHolder(private val binding: ItemOrderDetailsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val item = currentList[position]
            val booksAdapter by lazy { OrderDetailsBookAdapter() }
            booksAdapter.submitList(item.books)
            var bookCount: Int = 0
            var bookPrice: Int = 0
            item.books.forEach {
                bookCount += it.bookCount
                bookPrice += it.price
            }
            with(binding){
                tvOrderNumber.text = item.number.toString()
                tvTotalBooks.text = bookCount.toString()
                tvAllSum.text = bookPrice.toString()
                rvOrderBooks.adapter = booksAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOrderDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object{
        val ITEM_DIFF = object : DiffUtil.ItemCallback<OHistoryModel>(){
            override fun areItemsTheSame(oldItem: OHistoryModel, newItem: OHistoryModel): Boolean {
                return oldItem.number == newItem.number
                        && oldItem.books == newItem.books
            }

            override fun areContentsTheSame(oldItem: OHistoryModel, newItem: OHistoryModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}
