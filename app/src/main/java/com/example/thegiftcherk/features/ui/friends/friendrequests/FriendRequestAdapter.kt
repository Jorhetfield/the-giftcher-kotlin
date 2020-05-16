package com.example.thegiftcherk.features.ui.friends.friendrequests

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.setup.utils.extensions.inflate


class FriendRequestAdapter(
    private val friend: MutableList<Friend>,
    private val idRequest: MutableList<String>,
    private val listener: (Friend) -> Unit
) : RecyclerView.Adapter<FriendRequestHolder>() {
    override fun onBindViewHolder(
        friendHolder: FriendRequestHolder,
        position: Int
    ) {
        friend[position].apply { friendHolder.bind(this, idRequest[position], listener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestHolder {
        val inflatedView = parent.inflate(R.layout.item_friend_request, false)

        return FriendRequestHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return friend.size
    }
}

