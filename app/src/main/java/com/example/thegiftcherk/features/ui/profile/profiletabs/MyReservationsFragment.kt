package com.example.thegiftcherk.features.ui.profile.profiletabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import kotlinx.android.synthetic.main.fragment_my_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyReservationsFragment : BaseFragment() {
    val items: MutableList<Item> = mutableListOf()
    private lateinit var myListAdapter: MyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_my_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getReservedWishes()
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

    private fun getReservedWishes() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getReservedWishes()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value.reservedWishes
                    items.clear()

                    responseResult?.forEach { reservedWishes ->
                        reservedWishes.friendReservedWishes?.forEach {
                            items.add(it)
                        }
                    }

                    myListAdapter.notifyDataSetChanged()
                    hideKeyboard()
                }
                is ResponseResult.Error -> {
                }
                is ResponseResult.Forbidden -> {
                }
            }
            hideProgressDialog()
        }
    }

}