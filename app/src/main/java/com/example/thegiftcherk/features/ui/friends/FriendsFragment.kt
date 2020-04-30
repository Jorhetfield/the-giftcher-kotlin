package com.example.thegiftcherk.features.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.fragment_my_list.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

class FriendsFragment : BaseFragment(), SearchView.OnQueryTextListener  {
    private val friends: MutableList<Friend> = mutableListOf()
    private val friendsFiltered: MutableList<Friend> = mutableListOf()
    private lateinit var friendsAdapter: FriendsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_friends, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFriends()

        fabAddFriend?.setOnClickListener{
            val action = FriendsFragmentDirections.actionNavigationFriendsToAddFriendsFragment()
            Navigation.findNavController(view).navigate(action)
        }

        closeBut1.setOnClickListener {
            hideKeyboard()
            searchView?.setText("")
        }

        searchView1?.addTextChangedListener {
            onQueryTextChange(searchView1.text.toString())
        }

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerFriends.layoutManager = linearLayoutManager

        friendsAdapter = FriendsAdapter(friends) {}
        recyclerFriends.adapter = friendsAdapter
    }

    override fun onQueryTextChange(query: String): Boolean {
        val text = searchView1.text.toString().toLowerCase()
        if (text.isEmpty()) {
            friends.clear()
            getFriends()
        } else {
            val productsPrefs = Gson().fromJson(prefs.obsLocationAddress, Array<Friend>::class.java).toList()
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
        val text = searchView1.text.toString().toLowerCase()
        val filteredModelList = filter(friends, query)
        Collections.replaceAll(filteredModelList, friends, filteredModelList)

        if (text.isEmpty()) {
            friends.clear()
            getFriends()
        } else {
            val productsPrefs = Gson().fromJson(prefs.obsLocationAddress, Array<Friend>::class.java).toList()
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

    private fun getFriends() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getFriends()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value.friends

                    prefs.obsLocationAddress = responseResult?.json()
                    friends.clear()
                    friends.addAll(responseResult!!.toList())
                    friendsAdapter.notifyDataSetChanged()

                    responseResult.forEach {
                        logD("probando peticiones $it")
                    }
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

}