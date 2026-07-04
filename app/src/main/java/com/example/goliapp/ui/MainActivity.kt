package com.example.goliapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.goliapp.R
import com.example.goliapp.databinding.ActivityMainBinding
import com.example.goliapp.ui.home.HomeFragment
import com.example.goliapp.ui.standings.StandingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)

            val params = binding.bottomNav.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = systemBars.bottom
            binding.bottomNav.layoutParams = params
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.standingsFragment, R.id.favouritesFragment)
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isDetail = destination.id == R.id.matchDetailFragment

            binding.bottomNav.visibility = if (isDetail) View.GONE else View.VISIBLE
            binding.toolbar.visibility = if(isDetail) View.GONE else View.VISIBLE

            val params = binding.bottomNav.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = if (isDetail) 0 else params.bottomMargin
            binding.bottomNav.layoutParams = params

            invalidateMenu()
        }

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_toolbar_menu, menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                val isTable = navController.currentDestination?.id == R.id.standingsFragment
                menu.findItem(R.id.action_filter_league)?.isVisible = isTable
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_refresh -> {
                        refreshCurrentScreen(navHostFragment)
                        true
                    }
                    R.id.action_filter_league -> {
                        showLeagueSelector()
                        true
                    }
                    R.id.action_about -> {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.about_app)
                            .setMessage(R.string.about_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show()
                        true
                    }
                    else -> false
                }
            }
        }, this, Lifecycle.State.RESUMED)
    }

    private fun showLeagueSelector() {
        val leagues = arrayOf("Premier League", "La Liga", "Serie A", "Bundesliga", "Ligue 1")
        val ids = arrayOf(39, 140, 135, 78, 61)

        AlertDialog.Builder(this)
            .setTitle("Select League")
            .setItems(leagues) { _, which ->
                val targetLeagueId = ids[which]

                supportActionBar?.title = "${leagues[which]} Table"

                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
                val currentFragment = navHostFragment?.childFragmentManager?.primaryNavigationFragment

                if (currentFragment is StandingsFragment) {
                    currentFragment.changeLeague(targetLeagueId)
                }
            }
            .show()
    }

    private fun refreshCurrentScreen(navHostFragment: NavHostFragment) {
        val fragment = navHostFragment.childFragmentManager.primaryNavigationFragment
        when (fragment) {
            is HomeFragment -> fragment.refreshFromMenu()
            is StandingsFragment -> fragment.refreshFromMenu()
        }
    }
}