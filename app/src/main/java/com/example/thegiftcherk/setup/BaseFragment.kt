package com.example.thegiftcherk.setup

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.utils.extensions.hideProgressDialog
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.showProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject


abstract class BaseFragment : Fragment() {
    //region Vars
    val prefs: Prefs by inject()
    val customRepository by inject<Repository>()
    //endregion Vars

    fun checkAndRequestPermission(permission: String, codeRequest: Int): Boolean {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                    return true
                } else {
                    ActivityCompat.requestPermissions(it, arrayOf(permission), codeRequest)
                }
            } else {
                return true
            }
            return false
        }
        return false
    }

    fun showMessage(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun capitalizeWords(text: String){
        text.split(' ').joinToString(" ") { it.capitalize() }
    }

    fun showMessageAction(
        message: String,
        view: View,
        actionTitle: String,
        onClickAction: View.OnClickListener
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(actionTitle, onClickAction)
            .show()
    }

    fun showError(error: String, v: View) {
        with(Snackbar.make(v, error, Snackbar.LENGTH_SHORT)) {
            view.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorError
                )
            )
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(ContextCompat.getColor(v.context, R.color.colorOnError))
            show()
        }
    }

    fun showErrorLong(error: String, v: View) {
        with(Snackbar.make(v, error, Snackbar.LENGTH_LONG)) {
            view.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorError
                )
            )
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(ContextCompat.getColor(v.context, R.color.colorOnError))
            show()
        }
    }

    fun showProgressDialog() {
        if (activity != null) {
            (activity as BaseActivity).showProgressDialog()
        }
    }
//
//    fun showTaximetro() {
//        if (activity != null) {
//            (activity as BaseActivity).showTaximetro()
//        }
//    }

    fun hideProgressDialog() {
        if (activity != null) {
            (activity as BaseActivity).hideProgressDialog()
        }
    }


    fun hideKeyboard() {
        if (activity != null) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    fun isCurrentNightMode(): Boolean {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                true
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                false
            } // Night mode is active, we're using dark theme
            else -> false
        }
    }
    //endregion Methods

    /**
     * App Special Methods
     **/

    //region App Special Methods
    fun addTextWatcherEmail(inputEmailLayout: TextInputLayout) : TextWatcher {
        return object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmail()) {
                    inputEmailLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().isEmail()) {
                    inputEmailLayout.isErrorEnabled = false
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().isEmail()) {
                    inputEmailLayout.isErrorEnabled = true
                    inputEmailLayout.error = getString(R.string.error_email)
                }
            }
        }
    }

    fun textWatcherPass(inputPasswordLayout: TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isValidPassword()) {
                    inputPasswordLayout.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().isValidPassword()) {
                    inputPasswordLayout.isErrorEnabled = false
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().isValidPassword()) {
                    inputPasswordLayout.isErrorEnabled = true
                    inputPasswordLayout.error = getString(R.string.error_pass)
                }
            }
        }
    }

    fun addTextWatcherRequired(inputLayout: TextInputLayout) : TextWatcher {
        return object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                inputLayout.isErrorEnabled = s.toString().isEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                inputLayout.isErrorEnabled = s.toString().isEmpty()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputLayout.isErrorEnabled = s.toString().isEmpty()
            }
        }
    }




    companion object {
        const val REQUEST_LOCATION = 83
    }
    //endregion App Special Methods
}
