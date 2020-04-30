package com.example.thegiftcherk.features.ui.friends.friendrequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.friends.FriendsAdapter
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_add_friends.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendsRequestsFragment : BaseFragment() {
    private val friendsRequests: MutableList<Friend> = mutableListOf()
    private val idArray: MutableList<String> = mutableListOf()
    private val idRequest: MutableList<String> = mutableListOf()
    private lateinit var friendsAdapter: FriendRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add_friends, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView2?.visibility = View.GONE
        searchIV2.visibility = View.GONE
        closeBut2.visibility = View.GONE

        friendsRequests.clear()
        idArray.clear()
        getFriendsRequests()

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerAddFriends.layoutManager = linearLayoutManager

        friendsAdapter =
            FriendRequestAdapter(
                friendsRequests, idRequest
            ) {}
        recyclerAddFriends.adapter = friendsAdapter

    }

    private fun getFriendsRequests() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getFriendRequests()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value
                    responseResult.forEach {
                        idArray.add(it.userId.toString())
                        idRequest.add(it.requestId.toString())
                    }

                    idArray.forEach {
                        getSingleUser(it)
                    }
                    logD("ids de usuarios mandando peticiones $idArray")

                }
                is ResponseResult.Error -> {
                    showError(response.message, view!!)
                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, view!!)

                }
            }
//            hideProgressDialog()
        }
    }

    private fun getSingleUser(userId: String) {
        GlobalScope.launch(Dispatchers.Main) {
//            showProgressDialog()
            when (val response =
                customRepository.getSingleUser(userId)) {
                is ResponseResult.Success -> {
                    val responseResult = response.value
                    logD("singleUser $responseResult")

                    friendsRequests.add(response.value)
                    friendsAdapter.notifyDataSetChanged()

                }
                is ResponseResult.Error -> {
                    showError(response.message, view!!)
                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, view!!)

                }
            }
            hideProgressDialog()
        }
    }
    //todo pasarle la id de la peticion, no la del usuario
     fun acceptFriend(friendRequestId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.confirmFriend(friendRequestId)) {
                is ResponseResult.Success -> {
                    val responseResult = response.value
                    logD("singleUser $responseResult")
                    getFriendsRequests()
                }
                is ResponseResult.Error -> {
                }
                is ResponseResult.Forbidden -> {

                }
            }
            hideProgressDialog()
        }
    }

     fun rejectFriend(friendRequestId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.deleteFriendRequest(friendRequestId)) {
                is ResponseResult.Success -> {
                    val responseResult = response.value
                    logD("singleUser $responseResult")
                    getFriendsRequests()
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