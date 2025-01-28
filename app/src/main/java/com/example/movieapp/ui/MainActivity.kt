package com.example.movieapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)








        binding.apply {
            //Navigation
            navController = findNavController(R.id.navHost)
            bottomNav.setupWithNavController(navController)
            //Show bottom navigation
            navController.addOnDestinationChangedListener { _, destination, _ ->
                binding.apply {
                    when (destination.id) {


                        R.id.homeFragment -> bottomNav.isVisible = true

                        R.id.genreMoviesFragment -> bottomNav.isVisible = true


                        R.id.searchFragment -> bottomNav.isVisible = true

                        R.id.favoriteFragment -> bottomNav.isVisible = true

                        R.id.splashFragment->bottomNav.isVisible = false

                        else -> bottomNav.isVisible = false

                    }
                }
            }
        }
    }














    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
}
