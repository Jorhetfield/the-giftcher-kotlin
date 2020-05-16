package com.example.thegiftcherk.features.ui.friends

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_my_list_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

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

        if (!friend.imagePath.isNullOrEmpty()){

            Picasso.get()
                .load(friend.imagePath)
                .placeholder(R.drawable.ic_electronics)
                .into(view.image_IV)
        } else {
            Picasso.get()
                .load(R.drawable.ic_electronics)
                .placeholder(R.drawable.ic_electronics)
                .into(view.image_IV)
        }

        view.friend_messageCard?.setOnClickListener {
            val action =
                FriendsFragmentDirections.goFromFriendToFriendDetail(friend)
            Navigation.findNavController(this.view).navigate(action)
        }

        view.friend_messageCard?.setOnLongClickListener {
            MaterialAlertDialogBuilder(view.context, R.style.DialogTheme1)
                .setTitle("¿Quieres borrar a ${friend.username} de tu lista de amigos?")
                .setNegativeButton("No",null)
                .setPositiveButton("Si") { _, _ ->

                }.show()
            true
        }
    }
}