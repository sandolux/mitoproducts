package com.osandoval.mitoproducts.ui.main.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.databinding.ActivityMainBinding
import com.osandoval.mitoproducts.ui.main.viewmodel.MainViewModel
import com.osandoval.mitoproducts.ui.main.viewmodel.MainViewModelFactory
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewmodel by viewModels<MainViewModel>{
        MainViewModelFactory(
            SharedPreferences(this@MainActivity)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_products, R.id.nav_orders, R.id.nav_shopping_cart), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navView.menu.findItem(R.id.nav_sign_out).setOnMenuItemClickListener {
            viewmodel.signOut()
            this.finish()
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}