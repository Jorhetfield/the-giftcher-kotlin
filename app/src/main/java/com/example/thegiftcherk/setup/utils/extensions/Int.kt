package com.example.thegiftcherk.setup.utils.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


private val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics

fun Int.dp(): Int = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics).toInt()

fun Int.colorStateList(): ColorStateList = ColorStateList.valueOf(this)

fun Int.toBitmap(context: Context, @ColorRes tintColor: Int? = null): Bitmap? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, this) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

    // add the tint if it exists
    tintColor?.let {
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, it))
    }
    // draw it onto the bitmap
    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return bm
}