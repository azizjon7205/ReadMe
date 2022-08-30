package me.ruyeo.kitobz.ui.library.audio

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.MyLibraryAudioAdapter
import me.ruyeo.kitobz.databinding.FragmentSubMyLibraryAudioBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.utils.mylibrary.RecyclerItemTouchHelper
import viewBinding

class MyLAudioFragment : BaseFragment(R.layout.fragment_sub_my_library_audio) {
    private val binding by viewBinding { FragmentSubMyLibraryAudioBinding.bind(it) }
    private val audioAdapter by lazy { MyLibraryAudioAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.rvAudio.layoutManager = GridLayoutManager(requireContext(), 1)
        audioAdapter.submitList(getBooksName())
        binding.rvAudio.adapter = audioAdapter
        audioAdapter.onDeleteClick = {
            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        }
        audioAdapter.onPinClick = {
            Toast.makeText(requireContext(), "Pined", Toast.LENGTH_SHORT).show()
        }
        val helper = ItemTouchHelper(RecyclerItemTouchHelper(requireContext(), 230f).itemTouchHelper)
        helper.apply { attachToRecyclerView(binding.rvAudio) }
    }

    private fun getBooksName() : ArrayList<String>{
        val bookNames: ArrayList<String> = ArrayList()
        bookNames.add("До встречи с тобой")
        bookNames.add("12 сов")
        bookNames.add("Sapiens")
        bookNames.add("Танцующая с лошадьми")
        return bookNames
    }

}