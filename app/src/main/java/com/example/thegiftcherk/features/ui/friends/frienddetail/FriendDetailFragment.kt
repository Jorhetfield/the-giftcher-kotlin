package com.example.thegiftcherk.features.ui.friends.frienddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs.FriendDesireFragment
import com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs.FriendGiftsFragment
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.adapters.ViewPagerFragmentsAdapter
import com.example.thegiftcherk.setup.utils.TabLayoutMediator
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.friend_detail_fragment.*

class FriendDetailFragment : BaseFragment() {
    lateinit var friendTabsAdapter: ViewPagerFragmentsAdapter
    private val mFriend by lazyUnsychronized {
        arguments?.let {
            FriendDetailFragmentArgs.fromBundle(it).friend
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.friend_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs.friendId = mFriend?.id
        nombreusuario_TV?.text = "${mFriend?.name} ${mFriend?.lastName} (${mFriend?.username})"
        cumpleaños_TV?.text = mFriend?.birthday

        Picasso.get()
            .load(mFriend?.imagePath)
            .into(imageProfile)

        setTabBar()
    }

    private fun setTabBar() {
        activity?.let {
            friendTabsAdapter =
                ViewPagerFragmentsAdapter(it.supportFragmentManager, lifecycle)
            friendTabsAdapter.addFragment(FriendDesireFragment())
            friendTabsAdapter.addFragment(FriendGiftsFragment())
            containerTabs.adapter = friendTabsAdapter

            TabLayoutMediator(tabs, containerTabs) { tab, position ->
                when (position) {
                    TAB_DESIRES -> tab.text = "Deseos"
                    TAB_GIFTS_ASSOCIATEDS -> tab.text = "Regalos asociados"
                }
            }.attach()
        }
    }

    companion object {
        private const val TAB_DESIRES: Int = 0
        private const val TAB_GIFTS_ASSOCIATEDS: Int = 1
    }

}