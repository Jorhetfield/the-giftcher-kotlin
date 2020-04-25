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
    val items1: MutableList<Item> = mutableListOf()
    val items2: MutableList<Item> = mutableListOf()
    val items3: MutableList<Item> = mutableListOf()
    val items4: MutableList<Item> = mutableListOf()
    private lateinit var itemAdapter1: HomeAdapter
    private lateinit var itemAdapter2: HomeAdapter
    private lateinit var itemAdapter3: HomeAdapter
    private lateinit var itemAdapter4: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

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

        itemAdapter1 = HomeAdapter(
            items1
        ) {
        }

        itemAdapter2 = HomeAdapter(
            items2
        ) {
        }

        itemAdapter3 = HomeAdapter(
            items3
        ) {
        }

        itemAdapter4 = HomeAdapter(
            items4
        ) {
        }
        recycler1.adapter = itemAdapter1
        recycler2.adapter = itemAdapter2
        recycler3.adapter = itemAdapter3
        recycler4.adapter = itemAdapter4

        getItems()

        recyclerTitle1.text = CategoriesIds.VIDEOJUEGOS.category.second
        recyclerTitle2.text = CategoriesIds.MODA.category.second
        recyclerTitle3.text = CategoriesIds.TELEVISION.category.second
        recyclerTitle4.text = CategoriesIds.DEPORTE.category.second

    }

    override fun onResume() {
        super.onResume()
    }
    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getItems()) {
                is ResponseResult.Success -> {

                    val tech = response.value.filter {
                        it.category == CategoriesIds.VIDEOJUEGOS.category.first
                    }

                    val health =response.value.filter {
                        it.category == CategoriesIds.MODA.category.first
                    }

                    val clothing =response.value.filter {
                        it.category == CategoriesIds.TELEVISION.category.first
                    }

                    val collections =response.value.filter {
                        it.category == CategoriesIds.DEPORTE.category.first
                    }

                    items1.clear()
                    items2.clear()
                    items3.clear()
                    items4.clear()
                    items1.addAll(tech)
                    items2.addAll(health)
                    items3.addAll(clothing)
                    items4.addAll(collections)

                    itemAdapter1.notifyDataSetChanged()
                    itemAdapter2.notifyDataSetChanged()
                    itemAdapter3.notifyDataSetChanged()
                    itemAdapter4.notifyDataSetChanged()
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