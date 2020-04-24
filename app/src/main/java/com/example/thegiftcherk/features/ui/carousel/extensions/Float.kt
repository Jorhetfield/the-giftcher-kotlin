package es.vanadis.estaxitablet.features.carousel.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

private val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)