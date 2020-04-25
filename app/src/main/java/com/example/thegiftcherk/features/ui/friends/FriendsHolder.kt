package com.example.thegiftcherk.features.ui.friends

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendsHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var friend: Friend? = null
    private lateinit var listener: (Friend) -> Unit

    init {
        v.setOnClickListener {
            friend?.let {
            }
        }
    }

    fun bind(friend: Friend, listener: (Friend) -> Unit) {
        this.friend = friend
        this.listener = listener

        view.name_TV.text = friend.username
        view.birthday_TV.text = friend.birthday

        Picasso.get()
            .load(friend.imagePath)
            .into(view.image_IV)

        view.friend_messageCard?.setOnClickListener {
            val action =
                FriendsFragmentDirections.goFromFriendToFriendDetail(friend)
            Navigation.findNavController(this.view).navigate(action)
        }
    }
}