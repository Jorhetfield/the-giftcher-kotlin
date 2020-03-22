package com.example.thegiftcherk.features.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseActivity
import kotlinx.android.synthetic.main.activity_main_login.*

class MainLoginActivity : BaseActivity() {
    //region Vars
    //endregion Vars

    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_login)

        setStatusBarColor()

        setSupportActionBar(materialToolbarLogin)
        setupActionBarWithNavController(findNavController(R.id.fragment))
    }
    //endregion

    //region Methods
    //endregion Methods

    companion object {
        private val LOGTAG: String = MainLoginActivity::class.java.simpleName

        @JvmStatic
        fun intent(context: Context) =
            Intent(context, MainLoginActivity::class.java)
    }
}