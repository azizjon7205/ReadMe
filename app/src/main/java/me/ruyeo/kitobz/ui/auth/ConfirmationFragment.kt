package me.ruyeo.kitobz.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.MainActivity
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentConfirmationBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment(R.layout.fragment_confirmation) {
    private val binding by viewBinding { FragmentConfirmationBinding.bind(it) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
//        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            bnConfirm.setOnClickListener {
                Intent(requireContext(), MainActivity::class.java).also {
                    startActivity(it)
                }
                requireActivity().finish()
//                viewModel.verifyCode(smsCodeEt.text.toString())
            }
        }
    }

    private fun setupObservers() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Error -> {
                    binding.progressbar.visibility = View.GONE
                    showMessage(state.msg)
                    binding.bnConfirm.isEnabled = true
                }
                LoginState.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.bnConfirm.isEnabled = false
                }
                LoginState.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Intent(requireContext(), MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
                else -> Unit
            }
        }
    }
}