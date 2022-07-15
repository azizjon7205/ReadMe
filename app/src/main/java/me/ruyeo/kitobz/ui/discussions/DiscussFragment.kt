package me.ruyeo.kitobz.ui.discussions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.DiscussAdapter
import me.ruyeo.kitobz.databinding.FragmentDiscussBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class DiscussFragment : BaseFragment(R.layout.fragment_discuss) {

    private val binding by viewBinding { FragmentDiscussBinding.bind(it) }
    private val adapter by lazy { DiscussAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            rvDiscuss.adapter = adapter
        }
    }

    private fun setupObservers() {

    }
}