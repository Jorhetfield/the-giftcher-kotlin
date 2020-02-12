package es.vanadis.utg_estaxi_profesional.setup.utils.extensions

import android.widget.Button

fun Button.drawable(left: Int, top: Int, right: Int, bottom: Int) =
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom)
