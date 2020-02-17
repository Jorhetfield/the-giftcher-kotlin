package com.example.thegiftcherk.setup

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.utils.extensions.hideProgressDialog
import com.google.android.material.snackbar.Snackbar

import org.koin.android.ext.android.inject


@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {
    var progressDialog: Dialog? = null
    val prefs: Prefs by inject()


    fun showMessage(message: String, view: View = this.findViewById(android.R.id.content)) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showError(error: String, v: View = this.findViewById(android.R.id.content)) {
        with(Snackbar.make(v, error, Snackbar.LENGTH_SHORT)) {
            view.setBackgroundColor(ContextCompat.getColor(v.context, R.color.colorError))
            show()
        }
    }



    fun hideKeyboard() {
        with(getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
            this.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.SHOW_FORCED
            )
        }
    }

    fun setStatusBarColor() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
    }

    fun checkAndRequestPermission(permission: String, codeRequest: Int): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), codeRequest)
            }
        } else {
            return true
        }
        return false
    }

//    override fun onBackPressed() {
//        val fragment =
//            this.supportFragmentManager.findFragmentById(R.id.fragment)
//        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
//            super.onBackPressed()
//        }
//    }

    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }
}
