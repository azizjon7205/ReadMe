package me.ruyeo.kitobz.ui.audioplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.AudioListAdapter
import me.ruyeo.kitobz.databinding.FragmentAudioPlayerBinding
import me.ruyeo.kitobz.databinding.LayoutBottomSheetBinding
import me.ruyeo.kitobz.model.AudioItem
import viewBinding

class AudioListBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutBottomSheetBinding
    private lateinit var audioListAdapter: AudioListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = LayoutBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            audioListAdapter = AudioListAdapter()
            audioListAdapter.submitList(defaultList())
            var manager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

            rvAudioList.apply {
                layoutManager = manager
                adapter = audioListAdapter
            }
            icClose.setOnClickListener {
                dismiss()
            }

        }
    }

    private fun defaultList(): ArrayList<AudioItem> {
        var items = ArrayList<AudioItem>()
        items.add(AudioItem(1, "AudioItem", false, "10:11"))
        items.add(AudioItem(1, "AudioItem", false, "09:15"))
        items.add(AudioItem(1, "AudioItem", false, "10:11"))
        items.add(AudioItem(1, "AudioItem", false, "09:15"))
        items.add(AudioItem(1, "AudioItem", false, "10:11"))
        items.add(AudioItem(1, "AudioItem", false, "09:15"))
        items.add(AudioItem(1, "AudioItem", true, "10:11"))
        items.add(AudioItem(1, "AudioItem", false, "09:15"))
        return items
    }


}