package com.example.thegiftcherk.features.ui.home

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_row.view.*
import kotlinx.android.synthetic.main.item_home_row.view.homeItemCard
import kotlinx.android.synthetic.main.item_my_list_row.view.*
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.itemImage
import kotlinx.android.synthetic.main.item_row.view.message
import kotlinx.coroutines.flow.callbackFlow

class HomeHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var item: Item? = null
    private lateinit var listener: (Item) -> Unit

    init {
        v.setOnClickListener {
            item?.let {
                listener(it)
                logD("Click from holder home")
            }
        }
    }

    fun bind(item: Item, listener: (Item) -> Unit) {
        this.item = item
        this.listener = listener

        view.message.text = item.name

        if (!item.picture.isNullOrEmpty()){

            Picasso.get()
                .load(item.picture)
                .placeholder(R.drawable.ic_placeholder)
                .into(view.itemImage)
        } else {
            Picasso.get()
                .load(R.drawable.ic_placeholder)
                .into(view.itemImage)
        }

        view.homeItemCard?.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToProductDetailFragment(item)
            Navigation.findNavController(this.view).navigate(action)
        }

    }


}