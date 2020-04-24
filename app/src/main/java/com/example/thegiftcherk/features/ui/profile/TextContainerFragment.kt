package com.example.thegiftcherk.features.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import kotlinx.android.synthetic.main.fragment_text_container.*

class TextContainerFragment : BaseFragment() {

    private val mTextType by lazyUnsychronized {
        arguments?.let {
            TextContainerFragmentArgs.fromBundle(it).textType
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_text_container, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (mTextType) {
            0 -> textViewLong?.text = resources.getString(R.string.termsConditions)
            1 -> textViewLong?.text = resources.getString(R.string.privacyPolicy)
        }


    }

}