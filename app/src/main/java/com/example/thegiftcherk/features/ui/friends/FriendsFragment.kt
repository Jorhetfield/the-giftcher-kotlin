package com.example.thegiftcherk.features.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FriendsFragment : BaseFragment() {
    private val customRepository by inject<Repository>()
    private val friends: MutableList<Friend> = mutableListOf()
    private lateinit var friendsAdapter: FriendsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_friends, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getItems()


        val linearLayoutManager = LinearLayoutManager(context)
        recyclerFriends.layoutManager = linearLayoutManager

        friendsAdapter = FriendsAdapter(
            friends
        ) {

        }
        recyclerFriends.adapter = friendsAdapter
    }


    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getFriends()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value

                    friends.addAll(responseResult)
                    friendsAdapter.notifyDataSetChanged()


                    responseResult.forEach {
                        logD("probando peticiones $it")
                    }

                }

                is ResponseResult.Error -> {
                    showError("dsfghnfg", view!!)
                }
                is ResponseResult.Forbidden -> {
                }
            }
            hideProgressDialog()
        }
    }
}