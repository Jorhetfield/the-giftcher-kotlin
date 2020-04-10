package com.example.thegiftcherk.features.ui.friends

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class FriendsHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var friend: Friend? = null
    private lateinit var listener:(Friend) -> Unit

    init {
        v.setOnClickListener {
            friend?.let {
            }
        }
    }

    fun bind(friend: Friend, listener: (Friend) -> Unit) {
        this.friend = friend
        this.listener = listener

        view.name_TV.text = friend.name
        view.birthday_TV.text = friend.birthday

        Picasso.get()
            .load(friend.picture)
            .into(view.image_IV)

        view.friend_messageCard?.setOnClickListener {
            val action = FriendsFragmentDirections.goFromFriendToFriendDetail(friend)
            Navigation.findNavController(this.view).navigate(action)
        }
    }


}