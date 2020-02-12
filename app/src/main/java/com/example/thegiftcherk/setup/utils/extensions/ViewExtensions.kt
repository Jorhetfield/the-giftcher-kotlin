package es.vanadis.utg_estaxi_profesional.setup.utils.extensions

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.core.view.setPadding
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.google.android.material.snackbar.Snackbar


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

@SuppressLint("ResourceType")
inline fun View.snack(@IntegerRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    snack(resources.getString(messageRes), length, f)
}

fun View.slideStart(visible: Boolean, animDuration : Long = 600) {
    val transition : Transition =  Slide(Gravity.START)
    transition.apply {
        duration = animDuration
        addTarget(this@slideStart)
    }
    TransitionManager.beginDelayedTransition(this@slideStart.parent as ViewGroup, transition)
    this@slideStart.visibility = when(visible) {
        true -> {
            View.VISIBLE
        }
        false -> {
            View.GONE
        }
    }
}

fun View.slideStart(animDuration : Long = 600) {
    val transition : Transition =  Slide(Gravity.START)
    transition.apply {
        duration = animDuration
        addTarget(this@slideStart)
    }
    TransitionManager.beginDelayedTransition(this@slideStart.parent as ViewGroup, transition)
    this@slideStart.visibility = when(this@slideStart.visibility) {
        View.GONE -> {
            View.VISIBLE
        }
        View.VISIBLE -> {
            View.GONE
        }
        else -> {
            View.VISIBLE
        }
    }
}

fun View.slideEnd(show: Boolean, animDuration : Long = 600) {
    val transition : Transition =  Slide(Gravity.START)
    transition.apply {
        duration = animDuration
        addTarget(this@slideEnd)
    }
    TransitionManager.beginDelayedTransition(this@slideEnd.parent as ViewGroup, transition)
    this@slideEnd.visibility = when(show) {
        true -> {
            View.VISIBLE
        }
        false -> {
            View.GONE
        }
    }
}

fun View.slideEnd(animDuration : Long = 600) {
    val transition : Transition =  Slide(Gravity.START)
    transition.apply {
        duration = animDuration
        addTarget(this@slideEnd)
    }
    TransitionManager.beginDelayedTransition(this@slideEnd.parent as ViewGroup, transition)
    this@slideEnd.visibility = when(this@slideEnd.visibility) {
        View.GONE -> {
            View.VISIBLE
        }
        View.VISIBLE -> {
            View.GONE
        }
        else -> {
            View.VISIBLE
        }
    }
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

@SuppressLint("ResourceType")
fun Snackbar.action(@IntegerRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

fun View.visible() = this.post { if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE }

fun View.invisible() = this.post { if (this.visibility != View.INVISIBLE) this.visibility = View.INVISIBLE }

fun View.gone() = this.post { if (this.visibility != View.GONE) this.visibility = View.GONE }

fun View.colorize(from: Int, to: Int, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofInt(from, to).apply {
        setEvaluator(ArgbEvaluator())
        addUpdateListener { value ->
            this@colorize.setBackgroundColor((value.animatedValue as Int).colorStateList().defaultColor)
        }
        this.duration = duration
        startDelay = delay
    }.start()
}

fun View.alphaAnimated(from: Float, to: Float, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofFloat(from, to).apply {
        addUpdateListener { value ->
            this@alphaAnimated.alpha = value.animatedValue as Float
        }
        this.duration = duration
        startDelay = delay
    }.start()
}

fun View.colorizeTint(from: Int = this@colorizeTint.solidColor, to: Int, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofInt(from, to).apply {
        setEvaluator(ArgbEvaluator())
        addUpdateListener { value ->
            this@colorizeTint.backgroundTintList = (value.animatedValue as Int).colorStateList()
        }
        this.duration = duration
        startDelay = delay
    }.start()
}

fun View.paddingAnimated(from: Int, to: Int, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofInt(from, to).apply {
        addUpdateListener { value -> this@paddingAnimated.setPadding(value.animatedValue as Int) }
        this.duration = duration
        startDelay = delay
    }.start()
}

fun View.rotateAnimated(from: Float = this@rotateAnimated.rotation, to: Float, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofFloat(from, to).apply {
        addUpdateListener { value -> this@rotateAnimated.rotation = value.animatedValue as Float }
        this.duration = duration
        startDelay = delay
    }.start()
}

fun View.widhtAnimated(from: Int = this@widhtAnimated.width, to: Int, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofInt(from, to).apply {
        addUpdateListener { value -> this@widhtAnimated.layoutParams.width = value.animatedValue as Int }
        this.duration = duration
        startDelay = delay
    }.start()
}

fun View.heightAnimated(from: Int = this@heightAnimated.height, to: Int, duration: Long = 300, delay: Long = 0) {
    ValueAnimator.ofInt(from, to).apply {
        addUpdateListener { value -> this@heightAnimated.layoutParams.height = value.animatedValue as Int }
        this.duration = duration
        startDelay = delay
    }.start()
}