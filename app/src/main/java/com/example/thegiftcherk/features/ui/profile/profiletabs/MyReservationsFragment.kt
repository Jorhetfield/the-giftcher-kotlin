package com.example.thegiftcherk.features.ui.profile.profiletabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.utils.extensions.fromJson
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_my_list.*

class MyReservationsFragment : BaseFragment() {
    val items: MutableList<Item> = mutableListOf()
    private lateinit var myListAdapter: MyListAdapter
//    val wishList = prefs.wishIds?.fromJson<MutableList<Item>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_my_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 3)
        recyclerItemsMyList.layoutManager = gridLayoutManager
        myListAdapter =
            MyListAdapter(
//                wishList
            items
            ) {
            }
        recyclerItemsMyList.adapter = myListAdapter
    }

}