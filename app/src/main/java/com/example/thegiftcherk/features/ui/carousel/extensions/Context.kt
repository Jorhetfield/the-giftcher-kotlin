package es.vanadis.estaxitablet.features.carousel.extensions

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.color(color: Int) = ContextCompat.getColor(this, color)
