package com.example.thegiftcherk.features.ui.addproduct.productdetail

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
import com.example.thegiftcherk.features.ui.login.models.SendEditWish
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


class EditWishFragment : BaseFragment() {
    private lateinit var image: File
    private lateinit var wish: SendEditWish
    private val galleryRequestCode = 10
    private val cameraRequestCode = 20
    private var multipartPrueba: MultipartBody.Part? = null

    private val mProduct by lazyUnsychronized {
        arguments?.let {
            EditWishFragmentArgs.fromBundle(it).wish
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add_product, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        inputName?.setText(mProduct?.name)
        inputStore?.setText(mProduct?.shop)
        inputPrice?.setText(mProduct?.price)
        inputDescription?.setText(mProduct?.description)
        inputOnlineShop?.setText(mProduct?.onlineShop)

        if (!mProduct?.picture.isNullOrEmpty()) {

            Picasso.get()
                .load(mProduct?.picture)
                .into(imagePickerIV)
        } else {
            Picasso.get()
                .load(R.drawable.ic_placeholder)
                .into(imagePickerIV)
        }

        imagePickerIV?.setOnClickListener {
            selectImage()
        }
        saveButton?.setOnClickListener {
            if (inputName.text.toString()
                    .isNotEmpty() && inputStore.text.toString()
                    .isNotEmpty() &&  inputPrice.text.toString()
                    .isNotEmpty() && inputDescription.text.toString().isNotEmpty()
            ) {
                fetchData()
            } else {
                showError(
                    "Necesitamos que rellenes todos los campos",
                    constraintContainer
                )
            }
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
                        multipartPrueba = createMultipart(scaledBitmap(bitmap))
                        imagePickerIV?.setImageBitmap(scaledBitmap(bitmap))

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
                multipartPrueba = createMultipart(scaledBitmap(bitmap))
                imagePickerIV?.setImageBitmap(scaledBitmap(bitmap))

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

        return convertBitmapToFile(fileName, bitmap, qualityJpeg = 10)
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
                    showError(response.message, requireView())
                }
                is ResponseResult.Forbidden -> {
                    logD("******************** Forbidden *******************")
                    showError(response.message, requireView())
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

        wish = SendEditWish(
            inputName?.text.toString(),
            inputDescription?.text.toString(),
            inputPrice?.text.toString().toFloat(),
            inputStore?.text.toString(),
            mProduct?.picture,
            "",
            null,
            null,
            inputOnlineShop?.text.toString(),
            (spinnerCardType?.selectedItemPosition)?.plus(1)
        )

        editWish(mProduct?.id!!.toString(), wish)
        logD("productId = ${mProduct?.id}")
        logD("sendEditWish = $wish")
    }

    private fun editWish(wishId: String, sendEditWish: SendEditWish) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.editWish(wishId, sendEditWish)) {
                is ResponseResult.Success -> {

                    showMessage("Deseo editado correctamente", logoBackground)
                    findNavController().popBackStack()
                    if (multipartPrueba != null) {
                        multipartPrueba?.let { uploadWishImage(it, wishId) }
                    }

                }

                is ResponseResult.Error -> {
                    showError(response.message, constraintContainer)
                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, constraintContainer)
                }
            }
            hideProgressDialog()
        }
    }

}