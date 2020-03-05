package com.example.thegiftcherk.features.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.thegiftcherk.R
import kotlinx.android.synthetic.main.item_friend.view.*


class FriendsAdapter(private val mContext: FriendsFragment, private val listFriends: List<Friend>): ArrayAdapter<Friend>(mContext, 0, listFriends) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext!!).inflate(R.layout.item_friend, parent, false)

        val friend = listFriends[position]

        layout.name_TV.text = friend.name
        layout.birthday_TV.text = friend.birthday
        layout.image_IV.setImageResource(friend.image)

        return layout
    }

}