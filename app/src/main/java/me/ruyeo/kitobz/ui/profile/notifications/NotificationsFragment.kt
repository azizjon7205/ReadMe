package me.ruyeo.kitobz.ui.profile.notifications

import android.os.Bundle
import android.view.View
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentNotificationsBinding
import me.ruyeo.kitobz.ui.BaseFragment
import viewBinding

class NotificationsFragment : BaseFragment(R.layout.fragment_notifications) {
    private val binding by viewBinding { FragmentNotificationsBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
//        binding.swOnOffNews.isChecked
    }

}