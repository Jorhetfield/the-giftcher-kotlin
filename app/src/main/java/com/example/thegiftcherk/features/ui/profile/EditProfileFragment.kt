package com.example.thegiftcherk.features.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
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
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.constraintContainer
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
    private val galleryRequestCode = 10
    private val cameraRequestCode = 20
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
            ){
                selectImage()
            }

        }

        editPassword?.setOnClickListener {
            val action =
                EditProfileFragmentDirections.actionEditProfileFragmentToEditPasswordFragment()
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

    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Hacer foto", "Elegir desde la galería", "Eliminar", "Cancelar")
        val builder = AlertDialog.Builder(context)
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Hacer foto") {
                val policyBuilder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(policyBuilder.build())

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                val uri = Uri.parse("file:///sdcard/photo.jpg")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                //pic = f;
                startActivityForResult(intent, cameraRequestCode)

            } else if (options[item] == "Elegir desde la galería") {

                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                // Sets the type as image/*. This ensures only components of type image are selected
                intent.type = "image/*"
                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                val mimeTypes =
                    arrayOf("image/jpeg", "image/png")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                // Launching the Intent
                startActivityForResult(intent, galleryRequestCode)

            } else if (options[item] == "Eliminar") {

                MaterialAlertDialogBuilder(context)
                    .setTitle("¿Quieres borrar tu foto de perfil?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Si") { _, _ ->
                    }.show()

            } else if (options[item] == "Cancelar") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            galleryRequestCode -> {
                //data.getData returns the content URI for the selected Image
                data?.data?.let { uri ->
                    context?.contentResolver?.let { contentResolver ->
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            uri
                        )

                        logD("probando ${bitmap.width} ${bitmap.height}")

                        uploadImage(createMultipart(scaledBitmap(bitmap)))

                        imageProfile.setImageBitmap(scaledBitmap(bitmap))
                    }
                }
            }
            cameraRequestCode -> {
                logD("probando $data")

                val file = File(
                    Environment.getExternalStorageDirectory().path,
                    "photo.jpg"
                )
                val uri = Uri.fromFile(file)
                val bitmap: Bitmap
                bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                uploadImage(createMultipart(scaledBitmap(bitmap)))
                imageProfile.setImageBitmap(scaledBitmap(bitmap))

            }
        }
    }

    private fun scaledBitmap(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 1024, 768, true)
    }

    private fun createMultipart(bitmap: Bitmap): MultipartBody.Part {

        val imageFile = getFileFromBitmap("file", bitmap)

        val requestBodyFromFile: RequestBody =
            imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("file", "file", requestBodyFromFile)
    }

    private fun getFileFromBitmap(fileName: String, bitmap: Bitmap): File {

        return convertBitmapToFile(fileName, bitmap, qualityJpeg = 40)
    }

    private fun convertBitmapToFile(
        fileName: String,
        bitmap: Bitmap,
        qualityJpeg: Int = 40
    ): File {
        //Create a file to write bitmap data
        val file = File(context?.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos)
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
                    prefs.user = response.value.json()
                    prefs.token = response.value.token
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
                    prefs.user = response.value.json()
                    prefs.token = response.value.token
                    showMessage("Cambios realizados correctamente", view!!.rootView)
                    logD("response ${response.value}")

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