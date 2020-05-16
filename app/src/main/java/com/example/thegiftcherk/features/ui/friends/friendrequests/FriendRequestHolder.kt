package com.example.thegiftcherk.features.ui.friends.friendrequests

import android.graphics.Color
import android.view.View
import android.widget.Toast
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
            view.addFriendButton?.setBackgroundColor(Color.parseColor("#3AAA35"))
            Toast.makeText(view.context, "La petición ha sido aceptada",Toast.LENGTH_LONG).show()
        }
        view.deleteFriendButton?.setOnClickListener {
            fragment.rejectFriend(requestId)
            view.deleteFriendButton?.setBackgroundColor(Color.parseColor("#D93045"))
            Toast.makeText(view.context, "La petición ha sido rechazada",Toast.LENGTH_LONG).show()
        }
        view.name_TV.text = friend.username

        if (!friend.imagePath.isNullOrEmpty()){
            Picasso.get()
                .load(friend.imagePath)
                .placeholder(R.drawable.ic_gift)
                .into(view.image_IV)
        } else {
            Picasso.get()
                .load(R.drawable.ic_gift)
                .placeholder(R.drawable.ic_gift)
                .into(view.image_IV)
        }

    }
}