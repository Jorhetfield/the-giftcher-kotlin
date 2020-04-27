package com.example.thegiftcherk.setup.utils.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseActivity

private enum class States(val states: Int?) {
    NOT_ASSIGNED(1),
    ASSIGNED(2),
    ON_ROUTE(3),
    FINISHED(4),
}

fun BaseActivity.showProgressDialog() {

    hideProgressDialog()
    Dialog(this).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.dialog_progress)
        show()
        this@showProgressDialog.progressDialog = this
    }
}


fun BaseActivity.hideProgressDialog() {
    this.progressDialog?.dismiss()
}







