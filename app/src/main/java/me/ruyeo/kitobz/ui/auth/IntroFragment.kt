package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentIntroBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.utils.extensions.activityNavController
import me.ruyeo.kitobz.utils.extensions.navigateSafely
import viewBinding

@AndroidEntryPoint
class IntroFragment : BaseFragment(R.layout.fragment_intro) {
    private val binding by viewBinding { FragmentIntroBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            loginBtn.setOnClickListener {
                findNavController().navigate(R.id.action_introFragment_to_logInFragment)
            }

            skipBtn.setOnClickListener {
                activityNavController().navigateSafely(R.id.action_global_mainFlowFragment)

            }
        }
    }
}