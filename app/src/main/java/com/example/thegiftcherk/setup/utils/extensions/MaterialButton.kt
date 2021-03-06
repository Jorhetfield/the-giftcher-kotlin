package com.example.thegiftcherk.setup.utils.extensions

import android.animation.StateListAnimator
import com.example.thegiftcherk.R
import com.google.android.material.button.MaterialButton

fun MaterialButton.isEnableStyle(enabled: Boolean) {
    if (!enabled)
        this.apply {
            post {
                elevation = 0f.dp()
                stateListAnimator = null
                isEnabled = false
                setTextColor(context.color(android.R.color.white))
                backgroundTintList = context.color(R.color.light_gray).colorStateList()
            }
        }
    else
        this.apply {
            post {
                elevation = 2f.dp()
                stateListAnimator = StateListAnimator()
                isEnabled = true
                setTextColor(context.color(android.R.color.white))
                backgroundTintList = context.color(R.color.colorSecondary).colorStateList()
            }
        }
}