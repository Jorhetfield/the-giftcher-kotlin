package es.vanadis.utg_estaxi_profesional.setup.utils.extensions

import android.graphics.BlurMaskFilter
import android.view.View
import android.widget.TextView

fun TextView.blur(radius: Float) = this.apply {
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    if (radius == 0f) paint.maskFilter = null
    else paint.maskFilter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
}