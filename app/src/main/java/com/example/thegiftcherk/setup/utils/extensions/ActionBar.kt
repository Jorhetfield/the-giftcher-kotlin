package com.example.thegiftcherk.setup.utils.extensions

import android.animation.ValueAnimator
import androidx.appcompat.app.ActionBar

fun ActionBar.elevationAnimated(from: Float, to: Float, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofFloat(from, to).apply {
        addUpdateListener { value -> this@elevationAnimated.elevation = (value.animatedValue as Float) }
        this.duration = duration
        startDelay = delay
    }.start()
}