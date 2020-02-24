package com.example.thegiftcherk.features.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
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


        setSupportActionBar(materialToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    companion object {
        private val LOGTAG: String = MainActivity::class.java.simpleName

        @JvmStatic
        fun intent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}
