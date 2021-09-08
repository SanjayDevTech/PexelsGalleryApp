package com.sanjaydevtech.pexelsgallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sanjaydevtech.pexelsgallery.R
import com.sanjaydevtech.pexelsgallery.databinding.ActivityMainBinding
import com.sanjaydevtech.pexelsgallery.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.container.id) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        binding.bottomNavView.setOnItemReselectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.explore -> {
                    viewModel.exploreScrollToUp()
                }
                R.id.search -> {
                    viewModel.searchScrollToUp()
                }
            }
        }
    }
}