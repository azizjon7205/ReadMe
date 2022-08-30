package me.ruyeo.kitobz.ui.profile.orderhistory

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.OrderDetailsAdapter
import me.ruyeo.kitobz.databinding.FragmentOrdersHistoryBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.ui.basket.information.InfoModel
import viewBinding

class OrderHistoryFragment : BaseFragment(R.layout.fragment_orders_history) {
    private val binding by viewBinding { FragmentOrdersHistoryBinding.bind(it) }
    private val ordersAdapter by lazy { OrderDetailsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        ordersAdapter.submitList(getOrderHistoryList())
        binding.rvOrders.adapter = ordersAdapter

        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun getOrderHistoryList():ArrayList<OHistoryModel>{
        val items = ArrayList<OHistoryModel>()
        items.add(OHistoryModel(5771, getAllBooks1()))
        items.add(OHistoryModel(5743, getAllBooks2()))
        items.add(OHistoryModel(5560,getAllBooks3()))
        return items
    }

    private fun getAllBooks1(): ArrayList<InfoModel>{
        val books: ArrayList<InfoModel> = ArrayList()
        books.add(InfoModel("paper", true,1,120))
        books.add(InfoModel("electronic", false,1,25))
        return books
    }

    private fun getAllBooks2(): ArrayList<InfoModel>{
        val books: ArrayList<InfoModel> = ArrayList()
        books.add(InfoModel("paper", true,1,120))
        books.add(InfoModel("electronic", false,1,25))
        books.add(InfoModel("electronic", false,1,25))
        books.add(InfoModel("audio", false,1,60))
        books.add(InfoModel("paper", false,1,125))
        return books
    }

    private fun getAllBooks3(): ArrayList<InfoModel>{
        val books: ArrayList<InfoModel> = ArrayList()
        books.add(InfoModel("electronic", false,1,25))
        books.add(InfoModel("audio", false,1,60))
        books.add(InfoModel("paper", false,1,125))
        return books
    }


}