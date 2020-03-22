package com.example.thegiftcherk.features.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.SearchAdapter
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_row.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment() {
    val items: MutableList<Item> = mutableListOf()
    private lateinit var itemAdapter: HomeAdapter
    private val customRepository by inject<Repository>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)
    // Comentario de prueba de conexión

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager1 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager2= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler1.layoutManager = linearLayoutManager
        recycler2.layoutManager = linearLayoutManager1
        recycler3.layoutManager = linearLayoutManager2
        recycler4.layoutManager = linearLayoutManager3
        itemAdapter = HomeAdapter(
            items
        ) {
        }
        recycler1.adapter = itemAdapter
        recycler2.adapter = itemAdapter
        recycler3.adapter = itemAdapter
        recycler4.adapter = itemAdapter

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