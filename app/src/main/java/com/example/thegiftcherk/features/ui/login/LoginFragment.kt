package com.example.thegiftcherk.features.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.SendUser
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.features.ui.tutorial.TutorialActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


open class LoginFragment : BaseFragment() {
    //region Vars
    private lateinit var sendUser: SendUser
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Watch input
        inputPassword?.addTextChangedListener(textWatcherPass(inputPasswordLayout))
        //Set buttons click

        buttonLogin?.setOnClickListener {
            sendUser = SendUser(inputUsernameLogin?.text.toString(), inputPassword?.text.toString())
            onClickLogin()
//            pickFromGallery()
        }

        buttonRegister?.setOnClickListener {
            //            onClickLogin()
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)

        }

        buttonForgottenPass?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_forgottenPassFragment)
        }
    }
    //endregion Override Methods

    //region Methods

    //region Clicks
    private fun onClickLogin() {
        if (checkInputs()) {
            requestLogin(sendUser)
        }
    }

    private fun checkInputs(): Boolean {
        return if (inputUsernameLogin?.text.toString().isNotEmpty()
            && inputPassword?.text.toString()
                .isValidPassword()
        ) {
            true
        } else {
            if (inputUsernameLogin?.text.toString().isEmpty() || inputPassword?.text.toString().isEmpty()) {
                showError("No puede haber campos vacíos", constraintContainer)
            } else if (!inputPassword?.text.toString().isValidPassword()) {
                showError("Debes introducir una contraseña válida", constraintContainer)
            } else if (!inputUsernameLogin?.text.toString().isEmpty()
            ) {
                showError("Debes introducir un email válido", constraintContainer)
            }
            false
        }
    }
    //endregion Clicks

    private fun startTutorialActivity() {
        context?.let {
            val intent = TutorialActivity.intent(it)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun requestLogin(sendUser: SendUser) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.doLogin(sendUser)) {
                is ResponseResult.Success -> {
                    //Save User:
                    prefs.token = response.value.token
                    prefs.user = response.value.json()

                    logD("respuesta login ${response.value}")
                    logD("respuesta login ${prefs.firstLogin}")
                    //Change view:
                    if (prefs.firstLogin) {
                        startTutorialActivity()
                    } else {
                        startMainClientActivity()
                    }
                }
                is ResponseResult.Error ->
                    showError(response.message, constraintContainer)
                is ResponseResult.Forbidden ->
                    showError("ERROR", constraintContainer)
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
}