package me.ruyeo.kitobz.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.MainActivity
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentFirstBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class FirstFragment : BaseFragment(R.layout.fragment_first) {

    private val binding by viewBinding { FragmentFirstBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            loginBtn.setOnClickListener {
                findNavController().navigate(R.id.action_firstFragment_to_loginFragment)
            }

            skipBtn.setOnClickListener {
                Intent(requireActivity(),MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}