package com.example.thegiftcherk.features.ui.friends

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class FriendsHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var friend: Friend? = null
    private lateinit var listener:OnClickFriendsListener

    init {
        v.setOnClickListener {
            friend?.let {
                listener.onClickFriends(it)
            }
        }
    }

    fun bind(friend: Friend, listener: OnClickFriendsListener) {
        this.friend = friend
        this.listener = listener

        view.name_TV.text = friend.name
        view.birthday_TV.text = friend.birthday

        Picasso.get()
            .load(friend.picture)
            .into(view.image_IV)

        view.friend_messageCard?.setOnClickListener {
            view.findNavController().navigate(R.id.goFromFriendToFriendDetail)
        }
    }


}