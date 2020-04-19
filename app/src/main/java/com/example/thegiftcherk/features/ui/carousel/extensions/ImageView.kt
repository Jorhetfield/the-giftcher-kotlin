package es.vanadis.estaxitablet.features.carousel.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.picasso(url: String?, placeholder: Int, error: Int) = url?.let {
    if (url != "") Picasso.get().load(url).placeholder(placeholder).error(error).into(this)
    else this.setImageDrawable(context.getDrawable(error))
}