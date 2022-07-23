package me.ruyeo.kitobz.ui.library.electron

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.MyLibraryElectronicAdapter
import me.ruyeo.kitobz.databinding.FragmentSubMyLibraryElectronBinding
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.utils.utils.mylibrary.RecyclerItemTouchHelper
import viewBinding

class MyLElectronFragment : BaseFragment(R.layout.fragment_sub_my_library_electron) {
    private val binding by viewBinding { FragmentSubMyLibraryElectronBinding.bind(it) }
    private val electronicAdapter by lazy { MyLibraryElectronicAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.rvElectron.layoutManager = GridLayoutManager(requireContext(), 1)
        electronicAdapter.submitList(getBooksName())
        binding.rvElectron.adapter = electronicAdapter
        electronicAdapter.onDeleteClick = {
            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        }
        electronicAdapter.onPinClick = {
            Toast.makeText(requireContext(), "Pined", Toast.LENGTH_SHORT).show()
        }
        val helper = ItemTouchHelper(RecyclerItemTouchHelper(requireContext(), 180f).itemTouchHelper)
        helper.apply { attachToRecyclerView(binding.rvElectron) }
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