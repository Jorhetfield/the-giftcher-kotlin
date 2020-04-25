package com.example.thegiftcherk.features.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.SendEditPassword
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_edit_password.*
import kotlinx.android.synthetic.main.fragment_register.constraintContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditPasswordFragment : BaseFragment() {
    //region Vars
    private lateinit var sendEditPassword: SendEditPassword
    val user = Gson().fromJson(prefs.user, User::class.java)

    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_edit_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSave?.setOnClickListener {
            onClickRegister()
        }
    }
    //endregion Override Methods

    //region Methods
    private fun onClickRegister() {
        if (checkInputs()) {
            logD(
                "print inputs ${inputOldPassword?.text.toString() +
                        inputNewPassword?.text.toString() +
                        inputNewPasswordRepeat?.text.toString()}"
            )
            sendEditPassword = SendEditPassword(
                inputNewPassword?.text.toString(),
                inputOldPassword?.text.toString()
            )
            changePassword(sendEditPassword)
        }
    }

    private fun checkInputs(): Boolean {
        return if (inputOldPassword?.text.toString()
                .isNotEmpty() && inputNewPassword?.text.toString()
                .isNotEmpty() && inputNewPassword?.text.toString().isValidPassword()
            && inputNewPasswordRepeat?.text.toString()
                .isNotEmpty() && inputNewPassword?.text.toString() == inputNewPasswordRepeat?.text.toString()
        ) {
            true
        } else {
            if (inputOldPassword?.text.toString().isEmpty() && inputNewPassword?.text.toString()
                    .isEmpty() && inputNewPasswordRepeat?.text.toString()
                    .isEmpty()
            ) {
                showError("Todos los campos deben estar rellenos", constraintContainer)
            } else if (inputNewPassword?.text.toString() != inputNewPasswordRepeat?.text.toString()){
                showError("Las contraseñas deben ser iguales", constraintContainer)
            }else if (!inputNewPassword?.text.toString().isValidPassword() || !inputNewPasswordRepeat?.text.toString().isValidPassword()){
                showError("Las contraseñas deben contener mayus minus un numero y un simbolo", constraintContainer)
            }


            false
        }
    }

    private fun changePassword(
        sendEditPassword: SendEditPassword
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.changePassword(
                sendEditPassword
            )) {
                is ResponseResult.Success -> {
                    //Save User:
                    findNavController().popBackStack()
                    showMessage("Cambios realizados correctamente", view!!.rootView)
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

//endregion Methods
}