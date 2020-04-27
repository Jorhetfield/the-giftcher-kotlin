package com.example.thegiftcherk.features.ui.addproduct.productdetail

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
import com.example.thegiftcherk.features.ui.login.models.SendEditWish
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class EditWishFragment : BaseFragment() {
    private lateinit var image: File
    private lateinit var wish: SendEditWish

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
        inputLocation?.setText(mProduct?.location)

        Picasso.get()
            .load(mProduct?.picture)
            .into(imagePickerIV)


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

        wish = SendEditWish(
            inputName?.text.toString(),
            inputDescription?.text.toString(),
            inputPrice?.text.toString().toFloat(),
            inputStore?.text.toString(),
            mProduct?.picture,
            "",
            null,
            inputLocation?.text.toString(),
            null,
            null,
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

                    showMessage("Deseo añadido correctamente", logoBackground)
                    findNavController().popBackStack()

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