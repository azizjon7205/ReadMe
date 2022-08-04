package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemMyLibraryElectronicBinding
import java.lang.ref.WeakReference

class MyLibraryElectronicAdapter : RecyclerView.Adapter<MyLibraryElectronicAdapter.ViewHolder>(){

    private val diff = AsyncListDiffer(this, ITEM_DIFF)
    lateinit var onDeleteClick: (() -> Unit)
    lateinit var onPinClick: (() -> Unit)

    inner class ViewHolder(private val binding:ItemMyLibraryElectronicBinding) : RecyclerView.ViewHolder(binding.root){
        private val view =WeakReference(binding.root)
        fun bind(){
            val item = diff.currentList[adapterPosition]
            view.get()?.let {
                it.setOnClickListener {
                    if (view.get()?.scrollX != 0){
                        view.get()?.scrollTo(0,0)
                    }
                }
            }
            binding.apply {
                ivStateElectron.visibility = View.GONE
                bStartContinue.visibility = View.GONE
                llStateDownload.visibility = View.VISIBLE
                pbStateDownload.visibility = View.GONE
                tvBookName.text = item
                tvPercent.text = "67%"
                progressBar.progress = 67
                llPin.setOnClickListener {
                    onPinClick.invoke()
                }
                llDelete.setOnClickListener {
                    onDeleteClick.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMyLibraryElectronicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind()
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    fun submitList(items: ArrayList<String>){
        diff.submitList(items)
    }

    companion object{
        val ITEM_DIFF = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

}