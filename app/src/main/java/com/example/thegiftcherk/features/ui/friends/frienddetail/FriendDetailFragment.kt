package com.example.thegiftcherk.features.ui.friends.frienddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.friend_detail_fragment.*
import kotlinx.android.synthetic.main.item_friend.*
import kotlinx.android.synthetic.main.product_detail_fragment.*

class FriendDetailFragment : Fragment () {

    private val mFriend by lazyUnsychronized {
        arguments?.let {
           FriendDetailFragmentArgs.fromBundle(it).friend
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.friend_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nombreusuario_TV?.text = mFriend?.name
        cumpleaños_TV?.text = mFriend?.birthday

        Picasso.get()
            .load(mFriend?.picture)
            .into(imagefriend_IV)

    }

}