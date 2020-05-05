package com.example.thegiftcherk.features.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.thegiftcherk.BuildConfig
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.features.ui.main.MainLoginActivity
import com.example.thegiftcherk.setup.BaseActivity
import com.example.thegiftcherk.setup.utils.extensions.logD

class SplashActivity : BaseActivity() {
    //region Vars
    private var mDelayHandler: Handler? = null
    private val DELAY: Long = BuildConfig.SPLASH_DELAY
    //endregion Vars

    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay (Request first and delay after)
        mDelayHandler!!.postDelayed(mRunnable, DELAY)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
    //endregion Override Methods

    //region Methods
    private val mRunnable: Runnable = Runnable {
        checkToken()
        logD("tokenSpash ${prefs.token}")
    }


    private fun checkToken() {
        val intent: Intent
        if (!isFinishing) {
            intent = if (!prefs.token.isNullOrEmpty()) {
                MainActivity.intent(this)
            } else {
                MainLoginActivity.intent(this)
            }
            startActivity(intent)
            finish()
        }
    }


//endregion Methods
}