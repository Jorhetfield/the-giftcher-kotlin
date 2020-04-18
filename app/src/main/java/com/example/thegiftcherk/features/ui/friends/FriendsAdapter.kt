package com.example.thegiftcherk.features.ui.friends

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.inflate


class FriendsAdapter(
    private val friend: MutableList<Friend>,
    private val listener: (Friend) -> Unit
) : RecyclerView.Adapter<FriendsHolder>() {
    override fun onBindViewHolder(
        friendHolder: FriendsHolder,
        position: Int
    ) {
        friend[position].apply { friendHolder.bind(this, listener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val inflatedView = parent.inflate(R.layout.item_friend, false)

        return FriendsHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return friend.size
    }
}

