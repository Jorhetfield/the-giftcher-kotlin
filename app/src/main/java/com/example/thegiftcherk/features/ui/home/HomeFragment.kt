package com.example.thegiftcherk.features.ui.home

import android.content.Intent
import android.net.Uri
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
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : BaseFragment() {
    val items1: MutableList<Item> = mutableListOf()
    val items2: MutableList<Item> = mutableListOf()
    val items3: MutableList<Item> = mutableListOf()
    val items4: MutableList<Item> = mutableListOf()
    val likesMatching: MutableList<String?> = mutableListOf()
    private lateinit var itemAdapter1: HomeAdapter
    private lateinit var itemAdapter2: HomeAdapter
    private lateinit var itemAdapter3: HomeAdapter
    private lateinit var itemAdapter4: HomeAdapter
    private val arrPrueba: Array<String> = arrayOf("Videojuegos", "Hogar", "Televisión", "Moda")
    private val likes = prefs.likes?.fromJson<Array<String?>>() ?: arrPrueba
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFriendRequests()

        likes.forEach { like ->

            val prueba = if (resources.configuration.locale.toString() == "es_ES") {
                val dentro = CategoriesIds.values().find {
                    it.category.second == like
                }
                likesMatching.add(dentro?.category?.first)
                logD("prueba likes if $likesMatching")

            } else {
               val dentro =  CategoriesIdsEnglish.values().find {
                    it.category.second == like
                }
                likesMatching.add(dentro?.category?.first)
                logD("prueba likes else $likesMatching")

            }

            logD("Prueba $prueba ${resources.configuration.locale} ")
            logD("Prueba $likesMatching")
        }

        recyclerTitle1.text = getStringFromCategory(likesMatching[0] ?: "")
        recyclerTitle2.text = getStringFromCategory(likesMatching[1] ?: "")
        recyclerTitle3.text = getStringFromCategory(likesMatching[2] ?: "")
        recyclerTitle4.text = getStringFromCategory(likesMatching[3] ?: "")

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
        moreInfoButton?.setOnClickListener {
            openWeb("http://thegiftcher.com")
        }
    }

    private fun openWeb(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getAllWishes()) {
                is ResponseResult.Success -> {

                    val firstRecycler = response.value.filter {
                        it.category == likesMatching[0] ?: "1"
                    }

                    val secondRecycler = response.value.filter {
                        it.category == likesMatching[1] ?: "2"
                    }

                    val thirdRecycler = response.value.filter {
                        it.category == likesMatching[2] ?: "3"
                    }

                    val fourthRecycler = response.value.filter {
                        it.category == likesMatching[3] ?: "4"
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

    private fun getStringFromCategory(category: String): String {
        return when (category) {
            "1" -> resources.getString(R.string.likes_checkbox_videogames)
            "2" -> resources.getString(R.string.likes_checkbox_home)
            "3" -> resources.getString(R.string.likes_checkbox_motor)
            "4" -> resources.getString(R.string.likes_checkbox_homeappliances)
            "5" -> resources.getString(R.string.likes_checkbox_fashion)
            "6" -> resources.getString(R.string.likes_checkbox_garden)
            "7" -> resources.getString(R.string.likes_checkbox_tv)
            "8" -> resources.getString(R.string.likes_checkbox_music)
            "9" -> resources.getString(R.string.likes_checkbox_photo)
            "10" -> resources.getString(R.string.likes_checkbox_mobile)
            "11" -> resources.getString(R.string.likes_checkbox_computing)
            "12" -> resources.getString(R.string.likes_checkbox_sports)
            "13" -> resources.getString(R.string.likes_checkbox_books)
            "14" -> resources.getString(R.string.likes_checkbox_childs)
            "15" -> resources.getString(R.string.likes_checkbox_farming)
            "16" -> resources.getString(R.string.likes_checkbox_services)
            "17" -> resources.getString(R.string.likes_checkbox_collectibles)
            "18" -> resources.getString(R.string.likes_checkbox_masonry)
            "19" -> resources.getString(R.string.likes_checkbox_others)
            else -> "hola"
        }
    }

}