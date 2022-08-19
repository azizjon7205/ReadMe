package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemRepliesToAnswersBinding
import me.ruyeo.kitobz.model.Reply

class DiscussAnswerRepliesAdapter : ListAdapter<Reply, DiscussAnswerRepliesAdapter.ViewHolder>(ITEM_DIFF) {

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Reply>() {
            override fun areItemsTheSame(oldItem: Reply, newItem: Reply): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: Reply, newItem: Reply): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepliesToAnswersBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(private val binding: ItemRepliesToAnswersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val reply = currentList[adapterPosition]
            with(binding) {

                Glide.with(root).load(reply.owner_image).error(R.drawable.im_login).into(ivAvatar)
                tvReplierName.text = reply.owner_name
                tvReplyMessage.text = reply.message

            }
        }

    }

}