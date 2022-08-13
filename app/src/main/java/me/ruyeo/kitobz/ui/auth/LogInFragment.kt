package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentLoginBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class LogInFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            bnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_confirmationFragment)
//                viewModel.sendVerificationCode(etPhonenumber.text.toString(),requireActivity())
            }

            tvForgotPassword.setOnClickListener { findNavController().navigate(R.id.action_logInFragment_to_forgetPasswordFragment) }

            tvRegistration.setOnClickListener { findNavController().navigate(R.id.action_logInFragment_to_registrationFragment) }

            tvSkip.setOnClickListener {
                callActivityMain()
//                findNavController().popBackStack()
            }

        }
    }

    private fun setupObservers() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginState.CodeSent -> {
                    findNavController().navigate(R.id.action_logInFragment_to_confirmationFragment)
                }
                is LoginState.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Log.d("@@@", "Error: ${state.msg}")
                    showMessage(state.msg)
                    binding.bnLogin.isEnabled = true
                }
                LoginState.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.bnLogin.isEnabled = false
                }
                else -> Unit
            }
        }
    }
}