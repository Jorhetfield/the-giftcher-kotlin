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
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.friend_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment() {
    lateinit var requestsTabsAdapter: ViewPagerFragmentsAdapter
    val user = Gson().fromJson(prefs.user, User::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.friend_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabBar()
        imageProfile?.setOnClickListener {
            getProfileImage()
//            getWishImage("26")
            logD("responseClick")
        }

        logD("user $user")
        Picasso.get()
            .load(user?.imagePath)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageProfile)
        nombreusuario_TV?.text = user.username
        cumpleaños_TV?.text = user.birthday

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

    private fun getProfileImage() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getProfileImage()) {
                is ResponseResult.Success -> {
                    logD("response ${response}")

                }
                is ResponseResult.Error -> {
                    showError(response.message, view!!.rootView)
                    logD("response ${response.message}")
                }
                is ResponseResult.Forbidden ->
                    showError("ERROR", view!!.rootView)
            }
            hideProgressDialog()
        }
    }

    private fun getWishImage(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getWishImage(id)) {
                is ResponseResult.Success -> {
                    logD("response ${response.value}")

                }
                is ResponseResult.Error -> {
                    showError(response.message, view!!.rootView)
                    logD("response ${response.message}")
                }
                is ResponseResult.Forbidden ->
                    showError("ERROR", view!!.rootView)
            }
            hideProgressDialog()
        }
    }


    private fun startMainActivity() {
        context?.let {
            val intent = MainActivity.intent(it)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object {
        private const val TAB_WISH: Int = 0
        private const val TAB_RESERVATIONS: Int = 1
    }
}

