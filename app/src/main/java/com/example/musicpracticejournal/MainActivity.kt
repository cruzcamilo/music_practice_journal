package com.example.musicpracticejournal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.musicpracticejournal.databinding.ActivityMainBinding
import com.example.musicpracticejournal.util.ScreenList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewsIdArrayForBackButton: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupToolbar()
        viewsIdArrayForBackButton = ScreenList.screensWithBackButton
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        binding.myToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        setupBottomNavigationMenu()
    }

    private fun setupBottomNavigationMenu() {
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (viewsIdArrayForBackButton.contains(destination.id)) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
            if(destination.id == R.id.createFragment) {
                binding.bottomNav.visibility = View.INVISIBLE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

}