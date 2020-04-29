package com.example.thegiftcherk.features.ui.addproduct

import android.app.Activity
import android.app.AlertDialog
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.login.models.SendNewWish
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


class AddProductFragment : BaseFragment() {
    private lateinit var image: File
    private lateinit var wish: SendNewWish
    private val galleryRequestCode = 10
    private val cameraRequestCode = 20
    private lateinit var multipartPrueba: MultipartBody.Part

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add_product, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardTypes: MutableList<String> = mutableListOf()
        val prueba = resources.getStringArray(R.array.Categories)

        if (spinnerCardType != null) {
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    R.layout.spinner_row, prueba
                )
            }
            spinnerCardType.adapter = adapter
        }

        spinnerCardType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    context,
                    "nada seleccionado", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View, position: Int, id: Long
            ) {
            }
        }

        imagePickerIV?.setOnClickListener {
            selectImage()
        }
        saveButton?.setOnClickListener {
            fetchData()

        }

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
                        multipartPrueba = createMultipart(bitmap)

//                        uploadImage(createMultipart(bitmap))
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
//                uploadImage(createMultipart(bitmap))
                multipartPrueba = createMultipart(bitmap)
            }
        }
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

    private fun convertBitmapToFile(
        fileName: String,
        bitmap: Bitmap,
        qualityJpeg: Int = 100
    ): File {
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

    private fun uploadWishImage(file: MultipartBody.Part, wishId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response = customRepository.uploadWishImage(file, wishId)) {
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

    private fun fetchData() {

        inputName?.text
        inputStore?.text
        inputPrice?.text
        inputDescription?.text
        spinnerCardType?.selectedItemPosition

        logD(
            "inputsAddWish ${inputName?.text} ${inputStore?.text} ${inputPrice?.text} ${inputDescription?.text} ${spinnerCardType?.selectedItemPosition}"
        )
        fillData()

    }

    private fun fillData() {

        wish = SendNewWish(
            inputName?.text.toString(),
            inputDescription?.text.toString(),
            inputPrice?.text.toString(),
            inputStore?.text.toString(),
            false,
            inputLocation?.text.toString(),
            null,
            null,
            (spinnerCardType?.selectedItemPosition)?.plus(1)
        )

        addWish(wish)

    }


    private fun addWish(sendNewWish: SendNewWish) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.addWish(sendNewWish)) {
                is ResponseResult.Success -> {

                    showMessage("Deseo añadido correctamente", logoBackground)
                    uploadWishImage(multipartPrueba, response.value.id.toString())
//                    findNavController().popBackStack()

                }

                is ResponseResult.Error -> {
                }
                is ResponseResult.Forbidden -> {
                }
            }
            hideProgressDialog()
        }
    }

}