package com.example.thegiftcherk.features.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.fromJson
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {
    val items1: MutableList<Item> = mutableListOf()
    val items2: MutableList<Item> = mutableListOf()
    val items3: MutableList<Item> = mutableListOf()
    val items4: MutableList<Item> = mutableListOf()
    val likesMatching: MutableList<CategoriesIds?> = mutableListOf()
    private lateinit var itemAdapter1: HomeAdapter
    private lateinit var itemAdapter2: HomeAdapter
    private lateinit var itemAdapter3: HomeAdapter
    private lateinit var itemAdapter4: HomeAdapter
    private val arrPrueba: Array<String> = arrayOf("Videojuegos", "Hogar", "Televisión" ,"Moda")
    private val likes = prefs.likes?.fromJson<Array<String?>>() ?: arrPrueba
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFriendRequests()

        likes?.forEach { like ->
            val prueba = CategoriesIds.values().find {
                it.category.second == like
            }
            likesMatching.add(prueba)
            logD("Prueba $prueba")
            logD("Prueba $likesMatching")
        }
        recyclerTitle1.text = likesMatching[0]?.category?.second ?: ""
        recyclerTitle2.text = likesMatching[1]?.category?.second ?: ""
        recyclerTitle3.text = likesMatching[2]?.category?.second ?: ""
        recyclerTitle4.text = likesMatching[3]?.category?.second ?: ""


        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager1 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager2 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager3 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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

    }

    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getAllWishes()) {
                is ResponseResult.Success -> {

                    val firstRecycler = response.value.filter {
                        it.category == likesMatching[0]?.category?.first ?: "1"
                    }

                    val secondRecycler = response.value.filter {
                        it.category == likesMatching[1]?.category?.first ?: "2"
                    }

                    val thirdRecycler = response.value.filter {
                        it.category == likesMatching[2]?.category?.first ?: "3"
                    }

                    val fourthRecycler = response.value.filter {
                        it.category == likesMatching[3]?.category?.first ?: "4"
                    }

                    items1.clear()
                    items2.clear()
                    items3.clear()
                    items4.clear()
                    items1.addAll(firstRecycler)
                    items2.addAll(secondRecycler)
                    items3.addAll(thirdRecycler)
                    items4.addAll(fourthRecycler)

                    logD("prueba ****** ${response.value.size} ****** ${firstRecycler.size} ****** ${secondRecycler.size} ****** ${thirdRecycler.size} ****** ${fourthRecycler.size}")

                    itemAdapter1.notifyDataSetChanged()
                    itemAdapter2.notifyDataSetChanged()
                    itemAdapter3.notifyDataSetChanged()
                    itemAdapter4.notifyDataSetChanged()
                    hideKeyboard()
                }

                is ResponseResult.Error -> {
                    showError(response.message, recyclers)

                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, recyclers)

                }
            }
            hideProgressDialog()
        }
    }

    private fun getFriendRequests() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getFriendRequests()) {
                is ResponseResult.Success -> {
                    if (response.value.isNotEmpty()) {
                        //todo abrir diálogo
                        logD("Ok ${response.value}")
                        MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                            .setTitle("Tienes nuevas peticiones de amistad")
                            .setNegativeButton("Cerrar", null)
                            .setPositiveButton("Ver") { _, _ ->

                                findNavController().navigate(R.id.actionGoToFriendRequest)


                            }.show()
                    }
                }
                is ResponseResult.Error -> {
                    logD("Error")
                }
                is ResponseResult.Forbidden -> {
                    logD("Forbidden")

                }
            }
            hideProgressDialog()
        }
    }

}