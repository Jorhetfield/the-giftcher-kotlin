package com.example.thegiftcherk.setup

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText
import com.example.thegiftcherk.setup.utils.extensions.logD

@SuppressLint("AppCompatCustomView")
open class SearchTextView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null
) : EditText(context, attributeSet) {

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus()
            logD("afafasfasfasd")
            return true
        }
        return super.onKeyPreIme(keyCode, event)
    }

}
