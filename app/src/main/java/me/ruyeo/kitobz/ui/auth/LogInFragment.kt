package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentLoginBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

class LogInFragment:BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    fun setupUI(){
        binding.apply {
            bnLogin.setOnClickListener { findNavController().navigate(R.id.action_logInFragment_to_confirmationFragment)  }

            tvForgotPassword.setOnClickListener { findNavController().navigate(R.id.action_logInFragment_to_forgetPasswordFragment) }

            tvRegistration.setOnClickListener { findNavController().navigate(R.id.action_logInFragment_to_registrationFragment) }

            tvSkip.setOnClickListener { findNavController().popBackStack() }

        }
    }
}