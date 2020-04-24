package com.example.thegiftcherk.features.ui.addproduct

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class AddProductFragment : BaseFragment() {
    private lateinit var image: File
    private lateinit var wish: SendNewWish

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
            pickFromGallery()
        }
        saveButton?.setOnClickListener {
            fetchData()
        }

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
                val selectedImage: Uri? = data?.data
                val file = File(selectedImage?.path!!)
                image = file
                imagePickerIV?.setImageURI(selectedImage)
                val prueba = image.absoluteFile
                logD("selected image2 $selectedImage *** $file *** $prueba *** $image")
            }
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
            null,
            null,
            null,
            (spinnerCardType?.selectedItemPosition)?.plus(1)
        )

        addWish(wish)

    }


//    private fun uploadImageTest(image: File) {
//        GlobalScope.launch(Dispatchers.Main) {
//            showProgressDialog()
//            when (val response =
//                customRepository.uploadImage(image)) {
//                is ResponseResult.Success -> {
//                    //Save User:
//                    Toast.makeText(view?.context, "ok",Toast.LENGTH_LONG).show()
//
//                    //Change view:
//                }
//                is ResponseResult.Error ->
//                    showError(response.message, constraintContainer)
//                is ResponseResult.Forbidden ->
//                    showError("ERROR", constraintContainer)
//            }
//            hideProgressDialog()
//        }
//    }

    private fun addWish(sendNewWish: SendNewWish) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.addWish(sendNewWish)) {
                is ResponseResult.Success -> {

                    showMessage("Deseo añadido correctamente",logoBackground)
                    findNavController().popBackStack()

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