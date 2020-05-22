package com.example.thegiftcherk.features.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.features.ui.profile.profiletabs.MyListFragment
import com.example.thegiftcherk.features.ui.profile.profiletabs.MyReservationsFragment
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.adapters.ViewPagerFragmentsAdapter
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.TabLayoutMediator
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment() {
    lateinit var requestsTabsAdapter: ViewPagerFragmentsAdapter
    val user = Gson().fromJson(prefs.user, User::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabBar()
        imageProfile?.setOnClickListener {
            logD("responseClick")
        }
        getFriends()
        getItems()
        deleteFriendButton?.visibility = View.GONE

        logD("user $user")
        if (!user?.imagePath.isNullOrEmpty()){
            Picasso.get()
                .load(user?.imagePath)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageProfile)
        } else {
            Picasso.get()
                .load(R.drawable.ic_placeholder)
                .into(imageProfile)
        }

        nombreusuario_TV?.text = "${user.name} ${user.lastName} (${user.username})"
        cumpleaños_TVFecha?.text = user.birthday

    }

    private fun setTabBar() {
        activity?.let {
            requestsTabsAdapter =
                ViewPagerFragmentsAdapter(it.supportFragmentManager, lifecycle)
            requestsTabsAdapter.addFragment(MyListFragment())
            requestsTabsAdapter.addFragment(MyReservationsFragment())
            containerTabs.adapter = requestsTabsAdapter

            TabLayoutMediator(tabs, containerTabs) { tab, position ->
                when (position) {
                    TAB_WISH -> tab.text = "Deseos"
                    TAB_RESERVATIONS -> tab.text = "Reservas"
                }
            }.attach()
        }
    }
    private fun getFriends() {
        showProgressDialog()
        GlobalScope.launch(Dispatchers.Main) {
            when (val response =
                customRepository.getFriends()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value.friends
                    friendsNumber?.text = responseResult?.size.toString()
                }
                is ResponseResult.Error -> {
                    showError(response.message, requireView())
                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, requireView())

                }
            }

        }
    }

    private fun getItems() {
        GlobalScope.launch(Dispatchers.Main) {
            when (val response =
                customRepository.getItems()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value
                    wishesNumber?.text = responseResult.size.toString()
                }
                is ResponseResult.Error -> {
                }
                is ResponseResult.Forbidden -> {
                }
            }
            hideProgressDialog()
        }
    }

    companion object {
        private const val TAB_WISH: Int = 0
        private const val TAB_RESERVATIONS: Int = 1
    }
}

