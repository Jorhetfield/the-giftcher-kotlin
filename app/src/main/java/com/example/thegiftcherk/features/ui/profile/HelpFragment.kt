package com.example.thegiftcherk.features.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.tutorial.TutorialActivity
import com.example.thegiftcherk.setup.BaseFragment
import kotlinx.android.synthetic.main.fragment_help.*


class HelpFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_help, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchTutorial?.setOnClickListener {
            startTutorialActivity()
        }

        FAQButton?.setOnClickListener {
            openWeb("http://thegiftcher.com")
        }

        contactUsButton?.setOnClickListener {
            val action = HelpFragmentDirections.actionHelpFragmentToContactUsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun openWeb(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun startTutorialActivity() {
        context?.let {
            val intent = TutorialActivity.intent(it)
            startActivity(intent)
        }
    }

}