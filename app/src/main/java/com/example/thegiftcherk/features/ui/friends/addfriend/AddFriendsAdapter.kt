package com.example.thegiftcherk.features.ui.friends.addfriend

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.setup.utils.extensions.inflate


class AddFriendsAdapter(
    private val friend: MutableList<Friend>,
    private val listener: (Friend) -> Unit
) : RecyclerView.Adapter<AddFriendsHolder>() {
    override fun onBindViewHolder(
        friendHolderAdd: AddFriendsHolder,
        position: Int
    ) {
        friend[position].apply { friendHolderAdd.bind(this, listener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsHolder {
        val inflatedView = parent.inflate(R.layout.item_friend, false)

        return AddFriendsHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return friend.size
    }
}

