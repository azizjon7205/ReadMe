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
import me.ruyeo.kitobz.model.Answer
import me.ruyeo.kitobz.model.Discuss
import me.ruyeo.kitobz.utils.utils.extensions.visible

class DiscussAnswersAdapter : ListAdapter<Answer, DiscussAnswersAdapter.ViewHolder>(ITEM_DIFF) {

    var onClick: ((Answer) -> Unit)? = null

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Answer>() {
            override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscussMessageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(private val binding: ItemDiscussMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val answer = currentList[adapterPosition]
            with(binding) {

                Glide.with(root).load(answer.owner_image).error(R.drawable.ic_account).into(ivOwnerImage)
                tvAnswerOwner.text = answer.owner_name
                tvAnswerDate.text = answer.date
                tvAnswerMessage.text = answer.message

                if (answer.messages_count == 0){
                    flRepliesCount.visible(false)
                    tvAnswersText.text = root.context.getString(R.string.str_reply)
                } else{
                    flRepliesCount.visible(true)
                    tvAnswersCount.text = answer.messages_count.toString()
                    tvAnswersText.text = root.context.getString(R.string.str_answers_2)
                }

                llShowReplies.setOnClickListener {
                    onClick?.invoke(answer)
                }
            }
        }

    }

}