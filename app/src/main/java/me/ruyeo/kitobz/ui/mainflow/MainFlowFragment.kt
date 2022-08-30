package me.ruyeo.kitobz.ui.mainflow

import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.R
import me.ruyeo.kitobz.databinding.FragmentMainFlowBinding
import me.ruyeo.kitobz.ui.base.BaseFlowFragment
import me.ruyeo.kitobz.utils.extensions.visible
import viewBinding

class MainFlowFragment: BaseFlowFragment(R.layout.fragment_main_flow, R.id.nav_host_fragment_main) {
    private val binding by viewBinding {FragmentMainFlowBinding.bind(it)}

    override fun setupNavigation(navController: NavController) {
        super.setupNavigation(navController)

        binding.bnvMain.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment, R.id.basketFragment, R.id.myLibraryFragment, R.id.discussFragment, R.id.profileFragment2, R.id.basketOrderFragment -> {
                    binding.bnvMain.visible(true)
                }
                else -> {
                    binding.bnvMain.visible(false)
                }
            }
        }
    }
}