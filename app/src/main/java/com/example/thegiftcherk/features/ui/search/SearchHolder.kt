package com.example.thegiftcherk.features.ui.search

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*

class SearchHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var item: Item? = null
    private lateinit var listener: (Item) -> Unit

    init {
        v.setOnClickListener {
            item?.let {

                logD("hola ${it.name}")

            }
        }
    }

    fun bind(item: Item, listener: (Item) -> Unit) {
        this.item = item
        this.listener = listener

        view.subject.text = item.name
        view.message.text = item.description

        view.messageCard.setOnClickListener {
            val action = SearchFragmentDirections.goFromSearchToDetail(item)
            Navigation.findNavController(this.view).navigate(action)
        }

        Picasso.get()
            .load(item.picture)
            .noPlaceholder()
            .into(view.itemImage)
    }


}