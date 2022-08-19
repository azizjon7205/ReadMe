package me.ruyeo.kitobz.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.databinding.ItemSearchBinding
import me.ruyeo.kitobz.model.Book
import me.ruyeo.kitobz.utils.extensions.visible

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.BookVH>() {

    private val dif = AsyncListDiffer(this, ITEM_DIF)

    companion object{
        private val ITEM_DIF = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return true
            }

        }
    }

    inner class BookVH(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            val book = dif.currentList[adapterPosition]
            with(binding){
                if (book.is_discount){
                    tvSearchFee.visible(true)
                    tvSearchFee.paintFlags = tvSearchFee.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvSearchFee.text = "${book.price.toString()} somoni"
                    tvSearchFeeDiscount.text = "${book.discount_price.toString()} somoni"
                } else{
                    tvSearchFee.visible(false)
                    tvSearchFeeDiscount.text = "${book.price.toString()} somoni"
//                    tvSearchFeeDiscount.text = book.discount_price.toString()
                }
                tvSearchName.text = book.name
                tvSearchAuthor.text = book.author.toString()
                Glide.with(root)
                    .load(book.image)
                    .into(ivSearch)
                ivPaperBook.visible(book.hasPaperVersion)
                fEbook.visible(book.hasEVersion)
                fAudioBook.visible(book.hasAudio)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVH {
        return BookVH(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookVH, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = dif.currentList.size

    fun submitData(items: List<Book>){
        dif.submitList(items)
    }
}