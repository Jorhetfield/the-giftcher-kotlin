package com.example.thegiftcherk.features.ui.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.SendEditUser
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.addTenths
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.constraintContainer
import kotlinx.android.synthetic.main.fragment_register.inputName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.util.*

class EditProfileFragment : BaseFragment() {
    //region Vars
    private lateinit var sendUserRegister: SendEditUser
    val user = Gson().fromJson(prefs.user, User::class.java)

    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_edit_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputUsernameEdit?.setText(user.username)
        inputLastNameEdit?.setText(user.lastName)
        inputNameEdit?.setText(user.name)
        birthdayTextEdit?.setText(user.birthday)

        Picasso.get()
            .load(user.imagePath)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageProfile)

        datePickerImageEdit?.setOnClickListener {
            dialogBook()
        }
        buttonSave?.setOnClickListener {
            onClickRegister()
        }

        editImage?.setOnClickListener {
            pickFromGallery()
        }

        editPassword?.setOnClickListener {
            val action = EditProfileFragmentDirections.actionEditProfileFragmentToEditPasswordFragment()
            Navigation.findNavController(view).navigate(action)
        }

    }
    //endregion Override Methods

    //region Methods
    private fun onClickRegister() {
        if (checkInputs()) {

            logD(
                "print inputs ${inputNameEdit?.text.toString() +
                        inputLastname?.text.toString() +
                        inputEmail?.text.toString() +
                        inputUsername?.text.toString() +
                        inputPassword?.text.toString() +
                        birthdayText?.text.toString()}"
            )
            sendUserRegister = SendEditUser(
                inputNameEdit?.text.toString(),
                inputUsernameEdit?.text.toString(),
                inputLastNameEdit?.text.toString(),
                birthdayTextEdit?.text.toString()
            )
            editUserData(sendUserRegister)
        }
    }

    private fun checkInputs(): Boolean {
        return if (inputUsername?.text.toString().isNotEmpty() && inputName?.text.toString()
                .isNotEmpty()
            && inputLastname?.text.toString()
                .isNotEmpty() && birthdayText?.text.toString() != "Select your birthday"
        ) {
            true
        } else {
            showError("Todos los campos deben estar rellenos", constraintContainer)
            false
        }
    }


    private fun showDate(date: String) {
        birthdayTextEdit?.text = date
    }

    private fun pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        val intent = Intent(Intent.ACTION_PICK)
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.type = "image/*"
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        val mimeTypes =
            arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        // Launching the Intent
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            0 -> {
                //data.getData returns the content URI for the selected Image
                data?.data?.let { uri ->
                    context?.contentResolver?.let { contentResolver ->
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            uri
                        )

                        uploadImage(createMultipart(rotateBitmap(bitmap)))
                        imageProfile.setImageBitmap(rotateBitmap(bitmap))
                    }
                }
            }
        }
    }
    private fun rotateBitmap(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)

        return Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
    }

    private fun createMultipart(bitmap: Bitmap): MultipartBody.Part {

        val imageFile = getFileFromBitmap("file", bitmap)

        val requestBodyFromFile: RequestBody =
            imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("file", "file", requestBodyFromFile)
    }

    private fun getFileFromBitmap(fileName: String, bitmap: Bitmap): File {

        return convertBitmapToFile(fileName, bitmap, qualityJpeg = 100)
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap, qualityJpeg: Int = 100): File {
        //Create a file to write bitmap data
        val file = File(context?.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
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

    private fun uploadImage(file: MultipartBody.Part) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.uploadImage(file)) {
                is ResponseResult.Success -> {
                    //Save User:
                    logD("response ${response.value}")
                }

                is ResponseResult.Error -> {
                    logD("******************** Error *******************")
                    showError(response.message, view!!.rootView)
                }
                is ResponseResult.Forbidden -> {
                    logD("******************** Forbidden *******************")
                    showError(response.message, view!!.rootView)
                }
            }
            hideProgressDialog()
        }
    }

    private fun editUserData(
        sendEditUser: SendEditUser
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.editUser(
                sendEditUser
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