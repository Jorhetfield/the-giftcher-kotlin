package com.example.thegiftcherk.features.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.SendUserRegister
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.addTenths
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.birthdayText
import kotlinx.android.synthetic.main.fragment_register.constraintContainer
import kotlinx.android.synthetic.main.fragment_register.inputEmail
import kotlinx.android.synthetic.main.fragment_register.inputUsername
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class EditProfileFragment : BaseFragment() {
    //region Vars
    private lateinit var sendUserRegister: SendUserRegister
    val user = Gson().fromJson(prefs.user, User::class.java)

    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_edit_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputUsername?.setText(user.username)
        inputSurname?.setText(user.lastName)
        inputEmail?.setText(user.mail)
        birthdayTextEdit?.setText(user.birthday)

        Picasso.get()
            .load(user.imagePath)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageProfile)

        datePickerImageEdit?.setOnClickListener {
            dialogBook()
        }

    }

    //endregion Override Methods

    //region Methods
    private fun onClickRegister() {
        if (checkInputs()) {
//            if (checkAndRequestPermission(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION,
//                    REQUEST_LOCATION
//                )
//            ) {
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

    private fun checkInputs(): Boolean {
        return if (inputEmail?.text.toString().isEmail() && inputEmail?.text.toString().isNotEmpty()
            && inputPassword?.text.toString()
                .isNotEmpty() && inputPassword?.text.toString().length >= 6 && birthdayText?.text.toString() != "Select your birthday"
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
        birthdayTextEdit?.text = date
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
                    //Save User:
                    findNavController().popBackStack()
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
        private val LOGTAG: String = EditProfileFragment::class.java.simpleName
    }
}