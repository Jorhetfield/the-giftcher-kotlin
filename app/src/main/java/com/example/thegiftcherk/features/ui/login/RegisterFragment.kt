package com.example.thegiftcherk.features.ui.login

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.addTenths
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.dialog_date_picker.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.buttonConfirm
import kotlinx.android.synthetic.main.fragment_register.constraintContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

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

        datePickerImage?.setOnClickListener {
            dialogBook()
        }
    }

    //endregion Override Methods

    //region Methods
    private fun onClickRegister() {
        if (checkInputs()) {
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

    private fun checkInputs(): Boolean {
        return if (inputEmail?.text.toString().isEmail() && inputEmail?.text.toString().isNotEmpty()
            && inputPassword?.text.toString()
                .isNotEmpty() && inputPassword?.text.toString().length >= 6
        ) {
            true
        } else {
            if (inputEmail?.text.toString().isEmpty() && inputPassword?.text.toString().isEmpty()) {
                showError(getString(R.string.error_pass), constraintContainer)
            } else if (inputPassword?.text.toString().isValidPassword()) {
                showError(getString(R.string.error_pass), constraintContainer)
            } else if (!inputEmail?.text.toString().isEmail() || inputEmail?.text.toString()
                    .isEmpty()
            ) {
                showError(getString(R.string.error_email), constraintContainer)
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
                    "%s/%s/%s",
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