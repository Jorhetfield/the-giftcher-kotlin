package com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_my_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendGiftsFragment : BaseFragment() {
    private val desires: MutableList<Item> = mutableListOf()
    private lateinit var friendTabsAdapter: FriendTabsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_my_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFriendWishes(prefs.friendId!!.toLong())

        val gridLayoutManager = GridLayoutManager(context, 3)
        recyclerItemsMyList.layoutManager = gridLayoutManager

        friendTabsAdapter = FriendTabsAdapter(desires) {}
        recyclerItemsMyList.adapter = friendTabsAdapter
    }


    private fun getFriendWishes(userId: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getFriendWishes(userId)) {
                is ResponseResult.Success -> {
                    val responseResult = response.value

                    desires.clear()
                    desires.addAll(responseResult)
                    friendTabsAdapter.notifyDataSetChanged()

                    responseResult.forEach {
                        logD("probando peticiones $it")
                    }

                }

                is ResponseResult.Error -> {
                    showError(response.message, constraintContainerMyList)
                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, constraintContainerMyList)

                }
            }
            hideProgressDialog()
        }
    }
}