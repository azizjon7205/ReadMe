package me.ruyeo.kitobz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemDiscussMessageBinding
import me.ruyeo.kitobz.databinding.ItemDiscussionBinding
import me.ruyeo.kitobz.databinding.ItemRepliesToAnswersBinding
import me.ruyeo.kitobz.model.Answer
import me.ruyeo.kitobz.model.Discuss
import me.ruyeo.kitobz.model.Reply
import me.ruyeo.kitobz.utils.utils.extensions.visible

class DiscussAnswerRepliesAdapter : ListAdapter<Reply, DiscussAnswerRepliesAdapter.ViewHolder>(ITEM_DIFF) {

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Reply>() {
            override fun areItemsTheSame(oldItem: Reply, newItem: Reply): Boolean =
                oldItem.id == newItem.id

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

                Glide.with(root).load(reply.owner_image).error(R.drawable.ic_launcher_background).into(ivAvatar)
                tvReplierName.text = reply.owner_name
                tvReplyMessage.text = reply.message

            }
        }

    }

}