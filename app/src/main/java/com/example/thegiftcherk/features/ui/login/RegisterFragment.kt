package com.example.thegiftcherk.features.ui.login

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.addTenths
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.dialog_date_picker.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.buttonConfirm
import kotlinx.android.synthetic.main.fragment_register.constraintContainer
import kotlinx.android.synthetic.main.fragment_register.inputEmail
import kotlinx.android.synthetic.main.fragment_register.inputEmailLayout
import kotlinx.android.synthetic.main.fragment_register.inputPassword
import kotlinx.android.synthetic.main.fragment_register.inputPasswordLayout
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
            && inputPassword?.text.toString().isNotEmpty() && inputPassword?.text.toString().length >= 6
        ) {
            true
        } else {
            if (inputEmail?.text.toString().isEmpty() && inputPassword?.text.toString().isEmpty()) {
                showError(getString(R.string.error_pass), constraintContainer)
            }else if (inputPassword?.text.toString().isValidPassword() ) {
                showError(getString(R.string.error_pass), constraintContainer)
            }else if (!inputEmail?.text.toString().isEmail() || inputEmail?.text.toString().isEmpty()) {
                showError(getString(R.string.error_email), constraintContainer)
            }
            false
        }
    }

    private fun showDate(date: String){
        birthdayText.text = date
    }

    private fun dialogBook() {
        val calendar = Calendar.getInstance()
        val thisYear = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        val nowHour = calendar.get(Calendar.HOUR_OF_DAY)
        val nowMinute = calendar.get(Calendar.MINUTE)

        context?.let { context ->
            Dialog(context).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_date_picker)
                //Dialog
                buttonClose?.setOnClickListener {
                    cancel()
                }
                buttonConfirm?.setOnClickListener { view ->
                    if (textDate?.text.toString().isNotBlank() &&
                        textHourSelected?.text.toString().isNotBlank()
                    ) {
                        logD("logprueba ${textDate.text}")
                        prefs.fcmToken = textDate.text.toString()
                        logD("logpruebaprefs ${prefs.fcmToken}")
                        showDate(textDate.text.toString())
                        cancel()
                    } else {
                        showError(getString(R.string.error_pass), view)
                    }
                }
                buttonDate?.setOnClickListener {
                    val dpd = DatePickerDialog(
                        context,
                        R.style.DialogTheme,
                        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                            textDate?.text = String.format(
                                "%s/%s/%s",
                                year.toString().addTenths(),
                                (month + 1).toString().addTenths(),
                                dayOfMonth.toString().addTenths()
                            )
                        },
                        2000,
                        12,
                        11
                    )
                    dpd.datePicker
                    dpd.datePicker.minDate = System.currentTimeMillis() - 1000
                    dpd.show()
                }
                buttonHour?.setOnClickListener {
                    val tpd = TimePickerDialog(
                        context,
                        TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                            textHourSelected.text = String.format(
                                "%s:%s",
                                hour.toString().addTenths(),
                                minute.toString().addTenths()
                            )
                        },
                        nowHour,
                        nowMinute,
                        true
                    )
                    tpd.show()
                }
                //Change window options:
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.MATCH_PARENT
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
                window?.attributes = lp
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