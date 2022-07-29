package me.ruyeo.kitobz.ui.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentSplashBinding
import me.ruyeo.kitobz.ui.BaseFragment
import me.ruyeo.kitobz.utils.utils.UiStateObject
import viewBinding

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val binding by viewBinding { FragmentSplashBinding.bind(it) }
    private val viewModel by viewModels<LoginViewModel>()
    private var timer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        //button setOnClick
        val map = HashMap<String, Any>()
        map["email"] = "carwolt123@gmail.com"
        map["password"] = "12345678"
        viewModel.login(map)

        timer()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect {
                    when (it) {
                        is UiStateObject.LOADING -> {

                        }
                        is UiStateObject.SUCCESS -> {
                            Log.d("TAG", "setupObservers: ${it.data}")
                        }
                        is UiStateObject.ERROR -> {

                        }
                        else -> Unit
                    }
                }
            }
        }
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