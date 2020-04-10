package com.example.thegiftcherk.features.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.json
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*
import java.util.Collections.replaceAll

class SearchFragment : BaseFragment(), SearchView.OnQueryTextListener  {
    val items: MutableList<Item> = mutableListOf()
    val itemsQuery: MutableList<Item> = mutableListOf()
        private lateinit var itemAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeBut.setOnClickListener {
            hideKeyboard()
            searchView?.setText("")
        }

        searchView?.addTextChangedListener {
            onQueryTextChange(searchView.text.toString())
        }

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerItems.layoutManager = linearLayoutManager
        itemAdapter = SearchAdapter(
            items
        ) {
        }
        recyclerItems.adapter = itemAdapter
        getItems()
    }


    override fun onQueryTextChange(query: String): Boolean {
        val filteredModelList = filter(items, query)
//        replaceAll(filteredModelList, items, filteredModelList)
        val text = searchView.text.toString().toLowerCase()
        if (text.isEmpty()) {
            items.clear()
            getItems()
        } else {
            val productsPrefs = Gson().fromJson(prefs.servicesApplied, Array<Item>::class.java).toList()
            val name = ""
            val itemsQuery = productsPrefs.filter {
                if (it.name.isNullOrEmpty()) {
                    it.name == name
                } else {
                    it.name.toLowerCase().contains(text)
                }
            }
            items.clear()
            items.addAll(itemsQuery)
            itemAdapter.notifyDataSetChanged()

        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        val text = searchView.text.toString().toLowerCase()
        val filteredModelList = filter(items, query)
        replaceAll(filteredModelList, items, filteredModelList)

        if (text.isEmpty()) {
            items.clear()
            getItems()
        } else {
            val productsPrefs = Gson().fromJson(prefs.servicesApplied, Array<Item>::class.java).toList()
            val name = ""
            val itemsQuery = productsPrefs.filter {
                if (it.name.isNullOrEmpty()) {
                    it.name == name
                } else {
                    it.name.toLowerCase().contains(text)
                }
            }
            items.clear()
            items.addAll(itemsQuery)
            itemAdapter.notifyDataSetChanged()
            return true
        }
        hideKeyboard()
        return false

    }

    private fun filter(models: List<Item>, query: String): List<Item> {
        val lowerCaseQuery = query.toLowerCase()

        val filteredModelList = ArrayList<Item>()
        for (product in models) {
            val name = product.name?.toLowerCase()
            if (!name.isNullOrEmpty()) {
                if (name.contains(lowerCaseQuery)) {
                    filteredModelList.add(product)
                }
            }
            itemsQuery.addAll(filteredModelList)
        }
        return filteredModelList
    }


    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getItems()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value

                    prefs.servicesApplied = response.value.json()
                    items.clear()
                    items.addAll(responseResult)
                    itemAdapter.notifyDataSetChanged()
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