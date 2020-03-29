package com.example.thegiftcherk.features.ui.login

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.Repository
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.isEmail
import com.example.thegiftcherk.setup.utils.extensions.isValidPassword
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


open class LoginFragment : BaseFragment() {
    //region Vars
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Watch input
        inputEmail?.addTextChangedListener(addTextWatcherEmail(inputEmailLayout))
        inputPassword?.addTextChangedListener(textWatcherPass(inputPasswordLayout))
        //Set buttons click

        buttonLogin?.setOnClickListener {
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
            requestLogin(inputEmail?.text.toString(), inputPassword?.text.toString())
        }
    }

//    private fun pickFromGallery() {
//        //Create an Intent with action as ACTION_PICK
//        val intent = Intent(Intent.ACTION_PICK)
//        // Sets the type as image/*. This ensures only components of type image are selected
//        intent.type = "image/*"
//        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//        val mimeTypes =
//            arrayOf("image/jpeg", "image/png")
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//        // Launching the Intent
//        startActivityForResult(intent, 0)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) when (requestCode) {
//            0 -> {
//                //data.getData returns the content URI for the selected Image
//                val selectedImage: Uri? = data?.data
//                logD("selected image $selectedImage")
//                imageLogo?.setImageURI(selectedImage)
//
//            }
//        }
//    }

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
    //endregion Clicks

    private fun requestLogin(email: String, pass: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.doLogin(email, pass)) {
                is ResponseResult.Success -> {
                    //Save User:
                    prefs.token = response.value.token
                    prefs.user = response.value.json()

                    //Change view:
                    startMainClientActivity()
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

    companion object {
        private val LOGTAG: String = LoginFragment::class.java.simpleName
    }
}