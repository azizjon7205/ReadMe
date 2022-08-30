package me.ruyeo.kitobz.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.adapter.FavoriteAdapter
import me.ruyeo.kitobz.adapter.OrderHistoryAdapter
import me.ruyeo.kitobz.databinding.FragmentProfileBinding
import me.ruyeo.kitobz.ui.base.BaseFragment
import me.ruyeo.kitobz.ui.basket.information.InfoModel
import me.ruyeo.kitobz.ui.profile.orderhistory.OHistoryModel
import viewBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding by viewBinding { FragmentProfileBinding.bind(it) }
    private val ordersAdapter by lazy { OrderHistoryAdapter() }
    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding){
            ordersAdapter.submitList(getOrderHistoryList())
            rvOrders.adapter = ordersAdapter
            favoriteAdapter.submitList(getFavoriteBookNames())
            rvFavoriteBooks.adapter = favoriteAdapter

            llSeeAllOrders.setOnClickListener {
                findNavController().navigate(R.id.orderHistoryFragment)
            }
            ivPersonalData.setOnClickListener {
                findNavController().navigate(R.id.personalDataFragment)
            }
            ivNotifications.setOnClickListener {
                findNavController().navigate(R.id.notificationsFragment)
            }
            ivApplication.setOnClickListener {
                findNavController().navigate(R.id.applicationFragment)
            }
            llSeeAllFavorite.setOnClickListener {
                findNavController().navigate(R.id.favoriteFragment)
            }
        }
    }

    private fun getFavoriteBookNames(): ArrayList<String>{
        val bookNames = ArrayList<String>()
        bookNames.add("До встречи с тобой")
        bookNames.add("Не дружи со мной")
        bookNames.add("Один день в декабре")
        bookNames.add("Цветы для Элджернона")
        bookNames.add("Серебристая бухта")
        return bookNames
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