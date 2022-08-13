package me.ruyeo.kitobz.ui.pdfview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.ruyeo.kitobz.databinding.BottomSheetLayoutBinding


class BottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater)
        val view = binding.root

        initViews()

        return view
    }

    private fun initViews() {
        binding.apply {

        }
    }
}