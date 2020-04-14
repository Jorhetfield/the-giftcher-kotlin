package com.example.thegiftcherk.features.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.materialToolbar)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_add,
                R.id.navigation_friends,
                R.id.navigation_profile,
                R.id.navigation_search
            )
        )
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

//        NavigationUI.setupActionBarWithNavController(this, navController)

        NavigationUI.setupWithNavController(toolbar, navController)

        setStatusBarColor()
        setSupportActionBar(toolbar)
        navView.setupWithNavController(navController)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        val editIcon: View? = findViewById(R.id.edit)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_profile -> {
                    materialToolbar.visibility = View.VISIBLE
                    editIcon?.visibility = View.VISIBLE
                    menu?.findItem(R.id.edit)?.isVisible = true
                }
                else -> {
                    materialToolbar.visibility = View.VISIBLE
                    editIcon?.visibility = View.GONE
                    menu?.findItem(R.id.edit)?.isVisible = false
                }
            }
        }
        return super.onCreateOptionsMenu(menu)

    }

    companion object {
        private val LOGTAG: String = MainActivity::class.java.simpleName

        @JvmStatic
        fun intent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}
