package com.example.thegiftcherk.features.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_row.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment() {
    val items: MutableList<Item> = mutableListOf()
    private lateinit var itemAdapter: SearchAdapter
    private val customRepository by inject<Repository>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerItems.layoutManager = linearLayoutManager
        itemAdapter = SearchAdapter(
            items
        ) {
            messageCard.apply {
                
            }
        }
        recyclerItems.adapter = itemAdapter
        getItems()
    }

    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getItems()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value

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