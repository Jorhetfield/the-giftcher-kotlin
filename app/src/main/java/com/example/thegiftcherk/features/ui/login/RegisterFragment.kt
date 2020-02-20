package com.example.thegiftcherk.features.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment() {
    //region Vars
    private val customRepository by inject<Repository>()
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Watch input
        inputName?.addTextChangedListener(addTextWatcherRequired(inputNameLayout))
        inputEmail?.addTextChangedListener(addTextWatcherEmail(inputEmailLayout))
        inputPassword?.addTextChangedListener(addTextWatcherRequired(inputPasswordLayout))
        inputRepeatPassword?.addTextChangedListener(addTextWatcherRequired(inputRepeatPasswordLayout))

        //Set buttons click
        buttonConfirm.setOnClickListener {
            logD("probando inputs ${inputPassword.text} ////// ${inputRepeatPassword.text}")
            if (inputPassword.text.toString() == inputRepeatPassword.text.toString()) onClickRegister() else showError(
                "Las contraseñas deben ser iguales",
                view
            )
        }
    }

    //endregion Override Methods

    //region Methods
    private fun onClickRegister() {
        if (checkInputs(ArrayList<TextInputLayout>().apply {
                add(inputNameLayout)
                add(inputEmailLayout)
                add(inputPhoneLayout)
                add(inputPasswordLayout)
                add(inputRepeatPasswordLayout)
            })) {
            if (checkAndRequestPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    REQUEST_LOCATION
                )
            ) {
                requestRegister(
                    inputEmail?.text.toString(),
                    inputPassword?.text.toString(),
                    inputName?.text.toString(),
                    inputPhone?.text.toString()
                )
            }
        }
    }

    private fun requestRegister(email: String, pass: String, name: String, phone: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.doRegister(
                email,
                pass,
                name,
                phone
            )) {
                is ResponseResult.Success -> {
                    //Save User:

                    //Change view:

                }
                is ResponseResult.Error ->
                    showError(response.message, constraintContainer)
                is ResponseResult.Forbidden ->
                    showError(response.message, constraintContainer)
            }
            hideProgressDialog()
        }
    }

    private fun startMainClientActivity() {
        context?.let {
            val intent = MainActivity.intent(it)
            startActivity(intent)
            activity?.finish()
        }
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = RegisterFragment::class.java.simpleName
    }
}