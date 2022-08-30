package me.ruyeo.kitobz.ui.basket.information

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentBasketInformationBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import viewBinding

@AndroidEntryPoint
class InformationFragment : BaseFragment(R.layout.fragment_basket_information) {
    private val binding by viewBinding { FragmentBasketInformationBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}