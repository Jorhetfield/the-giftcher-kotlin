package com.example.thegiftcherk.features.ui.home

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.coroutines.flow.callbackFlow

class HomeHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var item: Item? = null
    private lateinit var listener: (Item) -> Unit

    init {
        v.setOnClickListener {
            item?.let {
                listener(it)
                logD("Click from holder")
            }
        }
    }

    fun bind(item: Item, listener: (Item) -> Unit) {
        this.item = item
        this.listener = listener

        view.message.text = item.description

        Picasso.get()
            .load(item.picture)
            .noPlaceholder()
            .into(view.itemImage)

    }


}