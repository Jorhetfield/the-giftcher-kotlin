package com.example.thegiftcherk.features.ui.login

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.SendUserRegister
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.features.ui.tutorial.TutorialActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class RegisterFragment : BaseFragment() {
    //region Vars
    private lateinit var sendUserRegister: SendUserRegister
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
        inputPassword?.addTextChangedListener(textWatcherPass(inputPasswordLayout))
        inputRepeatPassword?.addTextChangedListener(textWatcherPass(inputRepeatPasswordLayout))

        //Set buttons click
        buttonConfirm.setOnClickListener {
            logD("probando inputs ${inputPassword.text} ////// ${inputRepeatPassword.text}")
            if (inputPassword.text.toString() == inputRepeatPassword.text.toString()) onClickRegister() else showError(
                "Las contraseñas deben ser iguales",
                view
            )
        }

        datePickerImage?.setOnClickListener {
            dialogBook()
        }
    }

    //endregion Override Methods

    //region Methods
    private fun onClickRegister() {
        if (checkInputs()) {
            if (checkAndRequestPermission(
                    android.Manifest.permission.CAMERA,
                    CAMERA
                ) && checkAndRequestPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    CAMERA
                ) && checkAndRequestPermission(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    CAMERA
                ) && checkAndRequestPermission(
                    android.Manifest.permission.INTERNET,
                    CAMERA
                )
            ) {
                logD(
                    "print inputs ${inputName?.text.toString() +
                            inputLastname?.text.toString() +
                            inputEmail?.text.toString() +
                            inputUsername?.text.toString() +
                            inputPassword?.text.toString() +
                            birthdayText?.text.toString()}"
                )
                sendUserRegister = SendUserRegister(
                    inputName?.text.toString(),
                    inputUsername?.text.toString(),
                    inputLastname?.text.toString(),
                    inputEmail?.text.toString(),
                    inputPassword?.text.toString(),
                    birthdayText?.text.toString()
                )
                requestRegister(sendUserRegister)

            }
        }
    }

    private fun checkInputs(): Boolean {
        return if (inputEmail?.text.toString().isEmail() && inputEmail?.text.toString().isNotEmpty()
            && inputPassword?.text.toString().isValidPassword() && inputName?.text.toString()
                .isNotEmpty() && inputUsername?.text.toString()
                .isNotEmpty() && inputLastname?.text.toString().isNotEmpty()
            && inputPassword?.text.toString() == inputRepeatPassword?.text.toString()
            && birthdayText?.text.toString() != "Actualiza tu cumpleaños"
        ) {
            true
        } else {
            if (inputEmail?.text.toString().isEmpty() || inputPassword?.text.toString()
                    .isEmpty() || inputRepeatPassword.text.toString()
                    .isEmpty() || inputName?.text.toString()
                    .isEmpty() || inputUsername?.text.toString()
                    .isEmpty() || inputLastname?.text.toString()
                    .isEmpty() || birthdayText?.text.toString() == "Actualiza tu cumpleaños"
            ) {
                showError(getString(R.string.error_pass), constraintContainer)
            } else if (!inputPassword?.text.toString().isValidPassword()) {
                showError("Debes introducir una contraseña válida", constraintContainer)
            } else if (inputPassword?.text.toString() != inputRepeatPassword?.text.toString()) {
                showError("Las contraseñas deben ser iguales", constraintContainer)
            } else if (!inputEmail?.text.toString().isEmail()
            ) {
                showError(getString(R.string.error_email), constraintContainer)
            } else if (birthdayText?.text.toString() == "Actualiza tu cumpleaños") {
                showError("Debes seleccionar una fecha de cumpleaños", constraintContainer)
            }
            false
        }
    }

    private fun showDate(date: String) {
        birthdayText.text = date
    }

    private fun dialogBook() {
        val calendar = Calendar.getInstance()
        val thisYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        val nowHour = calendar.get(Calendar.HOUR_OF_DAY)
        val nowMinute = calendar.get(Calendar.MINUTE)
        var stringDate = ""

        val dpd = DatePickerDialog(
            context!!,
            R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                stringDate = String.format(
                    "%s-%s-%s",
                    year.toString().addTenths(),
                    (month + 1).toString().addTenths(),
                    dayOfMonth.toString().addTenths()
                )
                showDate(stringDate)
            },
            thisYear,
            month,
            today
        )
        dpd.datePicker.minDate = -System.currentTimeMillis()
        dpd.datePicker.maxDate = System.currentTimeMillis() - 1000
        dpd.show()
    }


    private fun requestRegister(
        sendUserRegister: SendUserRegister
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.doRegister(
                sendUserRegister
            )) {
                is ResponseResult.Success -> {

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

    private fun startTutorialActivity() {
        context?.let {
            val intent = TutorialActivity.intent(it)
            startActivity(intent)
            activity?.finish()
        }
    }

    //endregion Methods

    companion object {
        private val LOGTAG: String = RegisterFragment::class.java.simpleName
    }
}