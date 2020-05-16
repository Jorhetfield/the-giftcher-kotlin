package com.example.thegiftcherk.features.ui.friends.frienddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.frienddetail.friendtabs.FriendDesireFragment
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.adapters.ViewPagerFragmentsAdapter
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.TabLayoutMediator
import com.example.thegiftcherk.setup.utils.extensions.fromJson
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendDetailFragment : BaseFragment() {
    lateinit var friendTabsAdapter: ViewPagerFragmentsAdapter
    private val mFriend by lazyUnsychronized {
        arguments?.let {
            FriendDetailFragmentArgs.fromBundle(it).friend
        }
    }
    val userData = prefs.user?.fromJson<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.friend_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mFriend?.friendId == userData?.id) {
            deleteFriendButton?.visibility = View.GONE
        } else {
            deleteFriendButton?.visibility = View.VISIBLE
        }
        logD("friendId ${mFriend?.friendId} userId ${userData?.id}")

        prefs.friendId = mFriend?.friendId
        nombreusuario_TV?.text = "${mFriend?.name} ${mFriend?.lastName} (${mFriend?.username})"
        cumpleaños_TV?.text = mFriend?.birthday

        if (!mFriend?.imagePath.isNullOrEmpty()){

            Picasso.get()
                .load(mFriend?.imagePath)
                .placeholder(R.drawable.ic_electronics)
                .into(imageProfile)
        } else {
            Picasso.get()
                .load(R.drawable.ic_electronics)
                .placeholder(R.drawable.ic_electronics)
                .into(imageProfile)
        }

        setTabBar()

        deleteFriendButton?.setOnClickListener {
            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                .setTitle("¿De verdad quieres borrar a ${mFriend?.name} de tu lista de amigos?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si") { _, _ ->

                    deleteFriend(mFriend?.id!!)

                }.show()
        }

    }

    private fun setTabBar() {
        activity?.let {
            friendTabsAdapter =
                ViewPagerFragmentsAdapter(it.supportFragmentManager, lifecycle)
            friendTabsAdapter.addFragment(FriendDesireFragment())
            containerTabs.adapter = friendTabsAdapter

            TabLayoutMediator(tabs, containerTabs) { tab, position ->
                when (position) {
                    TAB_DESIRES -> tab.text = "Deseos"
                }
            }.attach()
        }
    }

    private fun deleteFriend(friendId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.deleteFriend(friendId)) {
                is ResponseResult.Success -> {

                    showMessage("Amigo borrado correctamente", constraintMessageInside)
                    findNavController().popBackStack()
                    logD("respuesta login ${response.value}")
                    logD("respuesta login ${prefs.firstLogin}")
                    //Change view:
                }
                is ResponseResult.Error ->
                    showError(response.message, constraintMessageInside)
                is ResponseResult.Forbidden ->
                    showError(response.message, constraintMessageInside)
            }
            hideProgressDialog()
        }
    }


    companion object {
        private const val TAB_DESIRES: Int = 0
        private const val TAB_GIFTS_ASSOCIATEDS: Int = 1
    }

}