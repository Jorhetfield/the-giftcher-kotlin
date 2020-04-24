package com.example.thegiftcherk.features.ui.carousel.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

private val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics

val Int.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics).toInt()