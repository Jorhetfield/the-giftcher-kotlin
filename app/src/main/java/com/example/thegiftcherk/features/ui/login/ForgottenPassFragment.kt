package com.example.thegiftcherk.features.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ForgottenPassFragment : BaseFragment() {
    //region Vars
    private val customRepository by inject<Repository>()
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_forgot_pass, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Watch input
        inputEmail?.addTextChangedListener(addTextWatcherEmail(inputEmailLayout))

        //Set buttons click
        buttonConfirm.setOnClickListener {
            onClickConfirm()
        }
    }
    //endregion Override Methods

    //region Methods
    private fun onClickConfirm() {
        if (checkInputs()) {
            requestRecoverPass()
        }
    }

    private fun checkInputs(): Boolean {
        return if (inputEmail?.text.toString().isEmail() &&
            inputEmail?.text.toString().isNotBlank()) {
            true
        } else {
            if (!inputEmail?.text.toString().isEmail()) {
                showError(getString(R.string.error_email), constraintContainer)
            } else if (inputEmail?.text.toString().isBlank()) {
                showError(getString(R.string.error_pass), constraintContainer)
            }
            false
        }
    }

    private fun requestRecoverPass() {
        val email = inputEmail.text.toString()
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.rememberPass(email)) {
                is ResponseResult.Success -> {
                    showMessage(response.value.message, view!!)
                }
                is ResponseResult.Empty ->
                    showError(response.message, constraintContainer)
                is ResponseResult.Error ->
                    showError(response.message, constraintContainer)
                is ResponseResult.Forbidden ->
                    showError(response.message, constraintContainer)
            }
            hideProgressDialog()
        }
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = ForgottenPassFragment::class.java.simpleName
    }
}