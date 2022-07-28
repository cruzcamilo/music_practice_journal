package com.example.musicpracticejournal.screens.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.musicpracticejournal.R
import com.example.musicpracticejournal.databinding.ActivityMainBinding
import com.example.musicpracticejournal.screens.home.HomeViewModel
import com.example.musicpracticejournal.screens.viewmodel.ViewModelFactory
import com.example.musicpracticejournal.util.ScreenList
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewsIdArrayForBackButton: ArrayList<Int>
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addTestPractice -> {
                viewModel.addMockPracticeFragment()
                true
            }
            R.id.deletePracticeFragments -> {
                viewModel.deleteAllMusicFragments()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }
}