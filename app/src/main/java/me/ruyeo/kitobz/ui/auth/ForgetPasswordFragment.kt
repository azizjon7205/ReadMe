package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentForgetpasswordBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

class ForgetPasswordFragment : BaseFragment(R.layout.fragment_forgetpassword) {
    val binding by viewBinding { FragmentForgetpasswordBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            bnConfirm.setOnClickListener {
                findNavController().navigate(R.id.action_forgetPasswordFragment_to_confirmationFragment)
            }

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}