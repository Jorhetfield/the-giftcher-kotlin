package com.example.thegiftcherk.features.ui.friends.addfriend

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.friends.friendrequests.FriendsRequestsFragment
import com.example.thegiftcherk.setup.network.FriendRequestId
import com.example.thegiftcherk.setup.network.Operation
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*

class AddFriendsHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View = v
    private var friend: Friend? = null
    private lateinit var listener: (Friend) -> Unit
    private lateinit var operationFriend: FriendRequestId

    init {
        v.setOnClickListener {
            friend?.let {
            }
        }
    }

    fun bind(friend: Friend, listener: (Friend) -> Unit) {
        this.friend = friend
        this.listener = listener
        val fragment = AddFriendsFragment()

        view.name_TV.text = friend.username
        view.birthday_TV.text = friend.birthday

        Picasso.get()
            .load(friend.imagePath)
            .into(view.image_IV)

        view.friend_messageCard?.setOnClickListener {
            MaterialAlertDialogBuilder(view.context, R.style.DialogTheme1)
                .setTitle("¿Quieres agregar a ${friend.username} a tu lista de amigos?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si") { _, _ ->
                    operationFriend = FriendRequestId("${friend.id}")
                    logD("operation $operationFriend")
                    fragment.addFriend(operationFriend)
                    Toast.makeText(view.context, "Petición enviada", Toast.LENGTH_LONG).show()

                }.show()
        }
    }
}