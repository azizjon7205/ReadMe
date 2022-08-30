package me.ruyeo.kitobz.ui.basket.orders

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.OrderHistoryAdapter
import me.ruyeo.kitobz.databinding.FragmentBasketOrdersBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.ui.basket.information.InfoModel
import me.ruyeo.kitobz.ui.profile.orderhistory.OHistoryModel
import viewBinding

@AndroidEntryPoint
class BasketOrderFragment : BaseFragment(R.layout.fragment_basket_orders) {
    private val binding by viewBinding { FragmentBasketOrdersBinding.bind(it) }
    private val ordersAdapter by lazy { OrderHistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        ordersAdapter.submitList(getOrderHistoryList())
        binding.rvOrders.adapter = ordersAdapter

        binding.llSeeAllOrders.setOnClickListener {
            findNavController().navigate(R.id.orderHistoryFragment)
        }
    }

    private fun getOrderHistoryList():ArrayList<OHistoryModel>{
        val items = ArrayList<OHistoryModel>()
        items.add(OHistoryModel(5771, getAllBooks3()))
        items.add(OHistoryModel(5743, getAllBooks3()))
        items.add(OHistoryModel(5560,getAllBooks3()))
        return items
    }

    private fun getAllBooks3(): ArrayList<InfoModel>{
        val books: ArrayList<InfoModel> = ArrayList()
        books.add(InfoModel("paper", true,1,120))
        books.add(InfoModel("electronic", false,1,25))
        return books
    }


}