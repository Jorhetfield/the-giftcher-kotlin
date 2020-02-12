package com.example.thegiftcherk.setup.utils.extensions

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.example.thegiftcherk.setup.utils.CircleTransform


fun ImageView.picasso(url: String?, placeholder: Int) = url?.let {
    if (url != "") Picasso.get().load(url).placeholder(placeholder).into(this)
}

fun ImageView.picassoCircle(url: String?, placeholder: Int) = url?.let {
    if (url != "") Picasso.get().load(url).transform(CircleTransform()).placeholder(placeholder).into(this)
}

fun MenuItem.picassoCircle(url: String?, placeholder: Int, resources: Resources) {
    val target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            this@picassoCircle.icon = BitmapDrawable(resources, bitmap)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
    }
    if (url != null && url != "") {
        Handler(Looper.getMainLooper()).post {
            Picasso.get().load(url).transform(CircleTransform()).placeholder(placeholder).into(target)
        }
    }
}
