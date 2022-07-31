package me.ruyeo.kitobz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.kitobz.databinding.ItemMyLibraryAudioBinding
import me.ruyeo.kitobz.databinding.ItemMyLibraryElectronicBinding
import me.ruyeo.kitobz.ui.library.search.SearchModel

class MyLibrarySearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val AUDIO = 0
    private val ELECTRONIC = 1

    private val diff = AsyncListDiffer(this, ITEM_DIFF)
    lateinit var onAudioClick: (() -> Unit)
    lateinit var onElectronicClick: (() -> Unit)

    inner class AudioViewHolder(private val binding:ItemMyLibraryAudioBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(audio: SearchModel){
            with(binding){
                flStateAudio.visibility = View.VISIBLE
                flStateDownload.visibility = View.GONE
                tvBookName.text = audio.name
                tvPercent.text = "67%"
                progressBar.progress = 67
                flStateAudio.setOnClickListener{
                    onAudioClick.invoke()
                }
            }
        }
    }

    inner class ElectronicViewHolder(private val binding:ItemMyLibraryElectronicBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(electronic: SearchModel){
            with(binding){
                bStartContinue.visibility = View.GONE
                llStateDownload.visibility = View.GONE
                ivStateElectron.visibility = View.VISIBLE
                tvBookName.text = electronic.name
                tvPercent.text = "67%"
                progressBar.progress = 67
                ivStateElectron.setOnClickListener {
                    onElectronicClick.invoke()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (diff.currentList[position].audioState)return AUDIO
        return ELECTRONIC
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == AUDIO) return AudioViewHolder(ItemMyLibraryAudioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return ElectronicViewHolder(ItemMyLibraryElectronicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AudioViewHolder) holder.bind(diff.currentList[position])
        if (holder is ElectronicViewHolder) holder.bind(diff.currentList[position])
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    fun submitList(items: ArrayList<SearchModel>){
        diff.submitList(items)
    }

    companion object{
        val ITEM_DIFF = object : DiffUtil.ItemCallback<SearchModel>(){
            override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
                return oldItem.audioState == newItem.audioState
                        && oldItem.electronicState == newItem.electronicState
                        && oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}