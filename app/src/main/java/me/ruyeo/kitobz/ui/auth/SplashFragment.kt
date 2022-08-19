package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentSplashBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val binding by viewBinding { FragmentSplashBinding.bind(it) }
    private val viewModel by viewModels<LoginViewModel>()
    private var timer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {


        timer()
    }

    private fun timer() {
        timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_introFragment)
            }
        }
        timer!!.start()
    }

}