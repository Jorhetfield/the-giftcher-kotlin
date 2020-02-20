package com.example.thegiftcherk.features.ui.splash

import android.os.Bundle
import android.os.Handler
import com.example.thegiftcherk.BuildConfig
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.features.ui.main.MainLoginActivity
import com.example.thegiftcherk.setup.BaseActivity

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
    }


    private fun checkToken() {
        var intent = MainLoginActivity.intent(this)
        if (!isFinishing) {
            if (!prefs.token.isNullOrEmpty()) {
                intent = MainActivity.intent(this)
            } else {
                intent = MainLoginActivity.intent(this)
            }
            startActivity(intent)
            finish()
        }
    }


//endregion Methods
}