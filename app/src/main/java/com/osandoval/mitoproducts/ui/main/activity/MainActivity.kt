package com.osandoval.mitoproducts.ui.main.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
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
import androidx.recyclerview.widget.GridLayoutManager
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.databinding.ActivityMainBinding
import com.osandoval.mitoproducts.ui.main.viewmodel.MainViewModel
import com.osandoval.mitoproducts.ui.main.viewmodel.MainViewModelFactory
import com.osandoval.mitoproducts.ui.products.adapter.ProductAdapter
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences

class MainActivity : AppCompatActivity() {
    private val TAG = "MITOPRODUCT"
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

        setHeaderData()
        setLogout()
    }

    private fun setHeaderData() {
        viewmodel.getUser().observe(this, { result->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    setFullName(result.data!!.fullName)
                    setEmail(result.data.email)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onViewCreated: ${ result.exception }")
                }
            }
        })
    }

    private fun setFullName(fullname: String?){
        binding.navView
            .getHeaderView(0)
            .findViewById<TextView>(R.id.text_view_header_username).text = fullname
    }

    private fun setEmail(email: String?){
        binding.navView
            .getHeaderView(0)
            .findViewById<TextView>(R.id.text_view_header_email).text = email
    }

    private fun setLogout() {
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