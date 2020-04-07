package com.example.thegiftcherk.features.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.addproduct.AddProductFragment
import com.example.thegiftcherk.features.ui.login.LoginFragment
import com.example.thegiftcherk.features.ui.login.RegisterFragment
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.features.ui.search.SearchFragment
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.adapters.ViewPagerFragmentsAdapter
import com.example.thegiftcherk.setup.utils.TabLayoutMediator
import kotlinx.android.synthetic.main.friend_detail_fragment.*

class ProfileFragment : BaseFragment() {
    lateinit var requestsTabsAdapter: ViewPagerFragmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.friend_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabBar()
    }

    private fun setTabBar() {
        activity?.let {
            requestsTabsAdapter =
                ViewPagerFragmentsAdapter(it.supportFragmentManager, lifecycle)
            requestsTabsAdapter.addFragment(AddProductFragment())
            requestsTabsAdapter.addFragment(SearchFragment())
            containerTabs.adapter = requestsTabsAdapter

            TabLayoutMediator(tabs, containerTabs) { tab, position ->
                when (position) {
                    TAB_LOGIN -> tab.text = getString(R.string.login)
                    TAB_REGISTER -> tab.text = getString(R.string.register)
                }
            }.attach()
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
        private const val TAB_LOGIN: Int = 0
        private const val TAB_REGISTER: Int = 1
    }
}

