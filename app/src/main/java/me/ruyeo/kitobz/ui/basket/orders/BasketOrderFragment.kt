package me.ruyeo.kitobz.ui.basket.orders

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentBasketOrdersBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

@AndroidEntryPoint
class BasketOrderFragment : BaseFragment(R.layout.fragment_basket_orders) {
    private val binding by viewBinding { FragmentBasketOrdersBinding.bind(it) }

    private var payType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            payType = it.getString("paymentType")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.tvPayType.text = payType
    }

}