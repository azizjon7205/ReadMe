package me.ruyeo.kitobz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.kitobz.databinding.ActivityMainBinding
import me.ruyeo.kitobz.utils.extensions.visible

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = findNavController(R.id.nav_host_main)
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