package io.raveerocks.gdgfinder.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import io.raveerocks.gdgfinder.R
import io.raveerocks.gdgfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
    }

    override fun onSupportNavigateUp() =
        navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)
    
    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when (destination.id) {
                R.id.home -> {
                    toolBar.setDisplayShowTitleEnabled(false)
                    binding.heroImage.visibility = View.VISIBLE
                }
                else -> {
                    toolBar.setDisplayShowTitleEnabled(true)
                    binding.heroImage.visibility = View.GONE
                }
            }
        }
    }
}
