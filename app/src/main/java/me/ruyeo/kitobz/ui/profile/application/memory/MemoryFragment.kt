package me.ruyeo.kitobz.ui.profile.application.memory

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentMemoryBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

@AndroidEntryPoint
class MemoryFragment : BaseFragment(R.layout.fragment_memory) {
    private val binding by viewBinding { FragmentMemoryBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
    }

}