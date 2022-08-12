package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemBookBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.utils.utils.extensions.visible

class AllFavoriteAdapter : ListAdapter<Book, AllFavoriteAdapter.ViewHolder>(ITEM_DIFF) {
    var onItemClick: ((Long) -> Unit)? = null
    var onFavoriteClick: ((Long) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(position: Int){
            val book = currentList[position]
            with(binding){
                llAllBookType.visibility = View.GONE
                ivFavorite.visibility = View.VISIBLE

                if (book.is_discount){
                    tvBookPriceOld.visible(true)
                    tvBookPriceOld.paintFlags = tvBookPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvBookPriceOld.text = "${book.price.toString()} сомони"
                    tvBookPrice.text = "${book.discount_price.toString()} сомони"
                } else{
                    tvBookPriceOld.visible(false)
                    tvBookPrice.text = "${book.price.toString()} сомони"
                }

                tvBookName.text = book.name
                tvBookAuthor.text = book.author.toString()
                Glide.with(root).load(book.image).into(ivBook)

                ivFavorite.setOnClickListener { onFavoriteClick?.invoke(book.id!!) }
                root.setOnClickListener { onItemClick?.invoke(book.id!!) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object{
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem == newItem
        }
    }

}