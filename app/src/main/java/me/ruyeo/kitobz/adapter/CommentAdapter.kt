package me.ruyeo.kitobz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemCommentsBinding
import me.ruyeo.kitobz.model.Feedback

class CommentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val feedback = dif.currentList[adapterPosition]
            with(binding) {
                Glide.with(root)
                    .load(feedback.author_img)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivOwnerImage)

                tvCommentOwner.text = feedback.author
                tvCommentDate.text = feedback.date
                tvComment.text = feedback.feedback
            }
        }
    }


    fun submitList(list: List<Feedback>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Feedback>() {
            override fun areItemsTheSame(oldItem: Feedback, newItem: Feedback): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Feedback, newItem: Feedback): Boolean =
                oldItem == newItem
        }
    }

}