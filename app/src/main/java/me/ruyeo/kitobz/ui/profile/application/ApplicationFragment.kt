package me.ruyeo.kitobz.ui.profile.application

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentApplicationBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

@AndroidEntryPoint
class ApplicationFragment : BaseFragment(R.layout.fragment_application) {
    private val binding by viewBinding { FragmentApplicationBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
        binding.ivManageMemory.setOnClickListener {
            findNavController().navigate(R.id.memoryFragment)
        }
    }

}