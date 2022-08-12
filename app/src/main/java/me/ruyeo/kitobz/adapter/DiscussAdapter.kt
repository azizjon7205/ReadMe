package me.ruyeo.kitobz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.ItemDiscussionBinding
import me.ruyeo.kitobz.model.Discuss

class DiscussAdapter : ListAdapter<Discuss, DiscussAdapter.ViewHolder>(ITEM_DIFF) {

    var onClick: ((Discuss) -> Unit)? = null
    var onClickFollow: ((Discuss) -> Unit)? = null

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Discuss>() {
            override fun areItemsTheSame(oldItem: Discuss, newItem: Discuss): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Discuss, newItem: Discuss): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscussionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(private val binding: ItemDiscussionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val discuss = currentList[adapterPosition]
            with(binding) {

                tvDiscussHeader.text = discuss.header
                tvDiscussAuthor.text = discuss.owner_name
                tvMessagesCount.text = "${discuss.messages_count} ${root.context.getString(R.string.str_messages)}"

                tvFollow.isActivated = discuss.followed
                checkFollow()
                tvFollow.setOnClickListener {
                    tvFollow.isActivated = !tvFollow.isActivated
                    checkFollow()
                    discuss.followed = tvFollow.isActivated
                    onClickFollow?.invoke(discuss)
                }

                root.setOnClickListener {
                    onClick?.invoke(discuss)
                }
            }
        }

        private fun checkFollow(){
            val tvFollow = binding.tvFollow

            if (tvFollow.isActivated){
                tvFollow.text = binding.root.context.getString(R.string.str_you_followed)
                tvFollow.setTextColor(Color.BLACK)
            } else{
                tvFollow.text = binding.root.context.getString(R.string.str_follow)
                tvFollow.setTextColor(Color.WHITE)
            }
        }
    }

}