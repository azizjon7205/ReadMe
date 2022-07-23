package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentRegisttrationBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registtration) {
    val binding by viewBinding { FragmentRegisttrationBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            bnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_chooseLanguageFragment)
            }
            tvLogin.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}