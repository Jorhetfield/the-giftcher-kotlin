package com.example.thegiftcherk.features.ui.profile.profiletabs

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.frienddetail.FriendDetailFragmentDirections
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.profile.ProfileFragment
import com.example.thegiftcherk.features.ui.profile.ProfileFragmentDirections
import com.example.thegiftcherk.features.ui.search.SearchFragmentDirections
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.fromJson
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_my_list_row.view.*
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.itemImage
import kotlinx.android.synthetic.main.item_row.view.message

class MyListHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view: View = v
    private var item: Item? = null
    private lateinit var listener: (Item?) -> Unit

    init {
        v.setOnClickListener {
            item?.let {
                logD("hola ${it.name}")
            }
        }
    }

    fun bind(item: Item, listener: (Item?) -> Unit) {
        this.item = item
        this.listener = listener

        view.homeItemCard?.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToProductDetailFragment(item)
            Navigation.findNavController(this.view).navigate(action)
        }

        if (item.reserved == true && item.userId == "26"){
            view.reservedText.visibility = View.VISIBLE
        } else {
            view.reservedText.visibility = View.GONE
        }

        view.message.text = item.name

        if (!item.picture.isNullOrEmpty()){

            Picasso.get()
                .load(item.picture)
                .placeholder(R.drawable.ic_gift)
                .into(view.itemImage)
        } else {
            Picasso.get()
                .load(R.drawable.ic_gift)
                .placeholder(R.drawable.ic_gift)
                .into(view.itemImage)
        }




    }


}