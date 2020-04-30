package com.example.thegiftcherk.features.ui.friends.addfriend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.friends.FriendsAdapter
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.FriendRequestId
import com.example.thegiftcherk.setup.network.Operation
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.appContext
import com.example.thegiftcherk.setup.utils.extensions.fromJson
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_friends.*
import kotlinx.android.synthetic.main.fragment_add_friends.constraintContainer
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.friend_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddFriendsFragment : BaseFragment(), SearchView.OnQueryTextListener {
    private val friends: MutableList<Friend> = mutableListOf()
    private val friendsFiltered: MutableList<Friend> = mutableListOf()
    private lateinit var friendsAdapter: AddFriendsAdapter
    val userData = prefs.user?.fromJson<User>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add_friends, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllUsers()

        closeBut2.setOnClickListener {
            hideKeyboard()
            searchView?.setText("")
        }

        searchView2?.addTextChangedListener {
            onQueryTextChange(searchView2.text.toString())
        }

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerAddFriends.layoutManager = linearLayoutManager

        friendsAdapter =
            AddFriendsAdapter(friends) {

            }
        recyclerAddFriends.adapter = friendsAdapter
    }

    override fun onQueryTextChange(query: String): Boolean {
        val text = searchView2.text.toString().toLowerCase()
        if (text.isEmpty()) {
            friends.clear()
//            getFriends()
        } else {
            val productsPrefs =
                Gson().fromJson(prefs.obsLocationAddress, Array<Friend>::class.java).toList()
            val name = ""
            val itemsQuery = productsPrefs.filter {
                if (it.username.isNullOrEmpty()) {
                    it.username == name
                } else {
                    it.username.toLowerCase().contains(text)
                }
            }
            friends.clear()
            friends.addAll(itemsQuery)
            friendsAdapter.notifyDataSetChanged()

        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        val text = searchView2.text.toString().toLowerCase()
        val filteredModelList = filter(friends, query)
        Collections.replaceAll(filteredModelList, friends, filteredModelList)

        if (text.isEmpty()) {
            friends.clear()
//            getFriends()
        } else {
            val productsPrefs =
                Gson().fromJson(prefs.obsLocationAddress, Array<Friend>::class.java).toList()
            val name = ""
            val itemsQuery = productsPrefs.filter {
                if (it.username.isNullOrEmpty()) {
                    it.username == name
                } else {
                    it.username.toLowerCase().contains(text)
                }
            }
            friends.clear()
            friends.addAll(itemsQuery)
            friendsAdapter.notifyDataSetChanged()
            return true
        }
        hideKeyboard()
        return false
    }

    private fun filter(models: List<Friend>, query: String): List<Friend> {
        val lowerCaseQuery = query.toLowerCase()

        val filteredModelList = ArrayList<Friend>()
        for (product in models) {
            val name = product.username?.toLowerCase()
            if (!name.isNullOrEmpty()) {
                if (name.contains(lowerCaseQuery)) {
                    filteredModelList.add(product)
                }
            }
            friendsFiltered.addAll(filteredModelList)
        }
        return filteredModelList
    }

    private fun getAllUsers() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getAllUsers()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value

                    responseResult.forEach {
                        logD("response ${it.username}")
                    }
                    val allUsers = response.value.filterNot {
                        it.username == userData?.username
                    }

                    prefs.obsLocationAddress = allUsers.json()
                    friends.clear()
                    friends.addAll(allUsers)
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

     fun addFriend(friendRequestId: FriendRequestId) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.createFriendRequest(friendRequestId)) {
                is ResponseResult.Success -> {
                    logD("respuesta login ${response.value}")
                }
                is ResponseResult.Error -> logD("respuesta login ${response.message}")

////                    showError(response.message, constraintContainer)
                is ResponseResult.Forbidden -> logD("respuesta login ${response.message}")
//                    showError(response.message, constraintContainer)
            }
            hideProgressDialog()
        }
    }

}