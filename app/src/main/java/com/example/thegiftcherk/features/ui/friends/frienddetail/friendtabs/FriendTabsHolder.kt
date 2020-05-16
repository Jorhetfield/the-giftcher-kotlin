package com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.frienddetail.FriendDetailFragmentDirections
import com.example.thegiftcherk.features.ui.search.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_my_list_row.view.*


class FriendTabsHolder(v: View) : RecyclerView.ViewHolder(v) {

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

        view.message.text = item.name

        if (!item.picture.isNullOrEmpty()){

            Picasso.get()
                .load(item.picture)
                .placeholder(R.drawable.ic_electronics)
                .into(view.itemImage)
        } else {
            Picasso.get()
                .load(R.drawable.ic_electronics)
                .placeholder(R.drawable.ic_electronics)
                .into(view.itemImage)
        }

        view.homeItemCard?.setOnClickListener {

            val action = FriendDetailFragmentDirections.actionFriendDetailFragmentToProductDetailFragment(item)
            Navigation.findNavController(this.view).navigate(action)

        }

    }


}