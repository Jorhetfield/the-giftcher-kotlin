package com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendDesireFragment : BaseFragment() {
    private val desires: MutableList<Item> = mutableListOf()
    private lateinit var friendDesireAdapter: FriendDesireAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_friends, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDesires()


        val linearLayoutManager = LinearLayoutManager(context)
        recyclerFriends.layoutManager = linearLayoutManager

        friendDesireAdapter = FriendDesireAdapter(desires) {}
        recyclerFriends.adapter = friendDesireAdapter
    }


    private fun getDesires() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getItems()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value

                    desires.clear()
                    desires.addAll(responseResult)
                    friendDesireAdapter.notifyDataSetChanged()

                    responseResult.forEach {
                        logD("probando peticiones $it")
                    }

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