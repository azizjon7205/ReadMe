package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.databinding.ItemBookBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.utils.utils.extensions.visible

class BookAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    var isPaperBook = true
    var isElectronicBook = true
    var isAudioBook = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }


    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val book = dif.currentList[adapterPosition]
            with(binding) {

                if (book.is_discount){
                    tvBookPriceOld.visible(true)
                    tvBookPriceOld.paintFlags = tvBookPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvBookPriceOld.text = "${book.price.toString()} somoni"
                    tvBookPrice.text = "${book.discount_price.toString()} somoni"
                } else{
                    tvBookPriceOld.visible(false)
                    tvBookPrice.text = "${book.price.toString()} somoni"
//                    tvSearchFeeDiscount.text = book.discount_price.toString()
                }
                tvBookName.text = book.name
                tvBookAuthor.text = book.author.toString()
                Glide.with(root)
                    .load(book.image)
                    .into(ivBook)

                fEbook.visible(isElectronicBook && book.hasEVersion)
                fPaperBook.visible(isPaperBook && book.hasPaperVersion)
                fAudioBook.visible(isAudioBook && book.hasAudio)

            }
        }
    }


    fun submitList(list: List<Book>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem
        }
    }


}