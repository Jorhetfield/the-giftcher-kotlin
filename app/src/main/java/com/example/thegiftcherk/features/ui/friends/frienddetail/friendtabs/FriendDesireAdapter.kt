package com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.inflate


class FriendDesireAdapter(
    private val items: MutableList<Item>,
    private val listener: (Item) -> Unit
) : RecyclerView.Adapter<FriendDesireHolder>() {


    //region Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendDesireHolder {
        val inflatedView = parent.inflate(R.layout.item_row, false)

        return FriendDesireHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size
    //endregion

    interface OnClickDesireListener {
        fun onClickDesires(it: Item)
    }


    override fun onBindViewHolder(holder: FriendDesireHolder, position: Int) {
        items[position].apply {
            holder.bind(this) { listener(it) }
        }
    }
}
