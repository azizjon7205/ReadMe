package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentLoginBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding { FragmentLoginBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}