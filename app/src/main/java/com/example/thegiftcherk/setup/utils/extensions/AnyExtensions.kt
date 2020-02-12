package com.example.thegiftcherk.setup.utils.extensions

import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import java.util.*

val Any.LOGTAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

val Any?.safe get() = Unit

const val FILTER = "DevFilter"

fun Any.logWTF(text: String) {
    Log.wtf("$FILTER: ${this::class.java.simpleName}", text)
}

fun Any.logWTF(text: String, exception: Exception) {
    Log.wtf("$FILTER: ${this::class.java.simpleName}", text, exception)
}

fun Any.logInfo(text: String) {
    Log.i("$FILTER: ${this::class.java.simpleName}", text)
}

fun Any.logInfo(text: String, exception: Exception) {
    Log.i("$FILTER: ${this::class.java.simpleName}", text, exception)
}

fun Any.logError(text: String) {
    Log.e("$FILTER: ${this::class.java.simpleName}", text)
}

fun Any.logError(text: String, exception: Exception) {
    Log.e("$FILTER: ${this::class.java.simpleName}", text, exception)
}

fun Any.logWarn(text: String) {
    Log.w("$FILTER: ${this::class.java.simpleName}", text)
}

fun Any.logWarn(text: String, exception: Exception) {
    Log.w("$FILTER: ${this::class.java.simpleName}", text, exception)
}

fun Any.logD(text: String) {
    Log.d("$FILTER: ${this::class.java.simpleName}", text)
}

fun Any.logD(text: String, exception: Exception) {
    Log.d("$FILTER: ${this::class.java.simpleName}", text, exception)
}

fun Any.logVerbose(text: String) {
    Log.v("$FILTER: ${this::class.java.simpleName}", text)
}

fun Any.logVerbose(text: String, exception: Exception) {
    Log.v("$FILTER: ${this::class.java.simpleName}", text, exception)
}

inline fun <T : View> T.afterMeasure(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

inline fun Timer.schedule(
    delay: Long,
    crossinline action: TimerTask.() -> Unit) {

}