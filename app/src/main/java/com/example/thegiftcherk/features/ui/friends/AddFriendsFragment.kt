package com.example.thegiftcherk.features.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_friends.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddFriendsFragment : BaseFragment(), SearchView.OnQueryTextListener {
    private val friends: MutableList<Friend> = mutableListOf()
    private val friendsFiltered: MutableList<Friend> = mutableListOf()
    private lateinit var friendsAdapter: FriendsAdapter


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

        friendsAdapter = FriendsAdapter(friends) {

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
                if (it.name.isNullOrEmpty()) {
                    it.name == name
                } else {
                    it.name.toLowerCase().contains(text)
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
                if (it.name.isNullOrEmpty()) {
                    it.name == name
                } else {
                    it.name.toLowerCase().contains(text)
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
            val name = product.name?.toLowerCase()
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

                    prefs.obsLocationAddress = responseResult.json()
                    friends.clear()
                    friends.addAll(responseResult)
                    friendsAdapter.notifyDataSetChanged()
                }

                is ResponseResult.Error -> {
                    showError("No estás en la build variant de MOCK.", view!!)
                }
                is ResponseResult.Forbidden -> {
                }
            }
            hideProgressDialog()
        }
    }

}