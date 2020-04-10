package com.example.thegiftcherk.features.ui.friends.frienddetail.frienddesire

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.features.ui.search.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*


class FriendDesireHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var item: Item? = null
    private lateinit var listener: (Item) -> Unit

    init {
        v.setOnClickListener {
            item?.let {

            }
        }
    }

    fun bind(item: Item, listener: (Item) -> Unit) {
        this.item = item
        this.listener = listener

        view.name_TV.text = item.name

        Picasso.get()
            .load(item.picture)
            .into(view.image_IV)
    }


}