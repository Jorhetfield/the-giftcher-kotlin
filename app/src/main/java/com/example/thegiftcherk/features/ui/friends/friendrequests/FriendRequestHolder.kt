package com.example.thegiftcherk.features.ui.friends.friendrequests

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.image_IV
import kotlinx.android.synthetic.main.item_friend.view.name_TV
import kotlinx.android.synthetic.main.item_friend_request.view.*

class FriendRequestHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var friend: Friend? = null
    private lateinit var listener: (Friend) -> Unit

    init {
        v.setOnClickListener {
            friend?.let {
            }
        }
    }

    fun bind(friend: Friend, requestId: String, listener: (Friend) -> Unit) {
        this.friend = friend
        this.listener = listener
        val fragment = FriendsRequestsFragment()
        view.addFriendButton?.setOnClickListener {
            fragment.acceptFriend(requestId)
            view.addFriendButton?.setBackgroundColor(Color.parseColor("#D93045"))
        }
        view.rejectFriendButton?.setOnClickListener {
            fragment.rejectFriend(requestId)
            view.addFriendButton?.setBackgroundColor(Color.parseColor("#D93045"))
        }
        view.name_TV.text = friend.username

        Picasso.get()
            .load(friend.imagePath)
            .into(view.image_IV)
    }
}