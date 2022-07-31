package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.view.View
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentConfirmationBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

class ConfirmationFragment: BaseFragment(R.layout.fragment_confirmation){
     val binding by viewBinding { FragmentConfirmationBinding.bind(it) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }
    private fun setupUI(){
        binding.apply {
            bnConfirm.setOnClickListener {
                callActivityMain()
            }
        }
    }
}