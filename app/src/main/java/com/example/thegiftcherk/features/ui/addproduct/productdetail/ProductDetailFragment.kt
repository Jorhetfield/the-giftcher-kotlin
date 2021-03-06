package com.example.thegiftcherk.features.ui.addproduct.productdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.friends.Friend
import com.example.thegiftcherk.features.ui.login.models.User
import com.example.thegiftcherk.features.ui.login.models.WishToReserve
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.example.thegiftcherk.setup.utils.extensions.fromJson
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDetailFragment : BaseFragment() {
    val userData = prefs.user?.fromJson<User>()
    val items: MutableList<String> = mutableListOf()
    private lateinit var wishToReserve: WishToReserve
    private var friends: MutableList<Friend> = mutableListOf()

    private val mProduct by lazyUnsychronized {
        arguments?.let {
            ProductDetailFragmentArgs.fromBundle(it).product
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.product_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logD("producto $mProduct")
        getFriends()
        getReservedWishes()
        when (mProduct?.category) {
            "1" -> categoryTV?.text = CategoriesIds.VIDEOJUEGOS.category.second
            "2" -> categoryTV?.text = CategoriesIds.HOGAR.category.second
            "3" -> categoryTV?.text = CategoriesIds.MOTOR.category.second
            "4" -> categoryTV?.text = CategoriesIds.ELECTRODOMESTICOS.category.second
            "5" -> categoryTV?.text = CategoriesIds.MODA.category.second
            "6" -> categoryTV?.text = CategoriesIds.JARDIN.category.second
            "7" -> categoryTV?.text = CategoriesIds.TELEVISION.category.second
            "8" -> categoryTV?.text = CategoriesIds.MUSICA.category.second
            "9" -> categoryTV?.text = CategoriesIds.FOTO.category.second
            "10" -> categoryTV?.text = CategoriesIds.MOVILES.category.second
            "11" -> categoryTV?.text = CategoriesIds.INFORMATICA.category.second
            "12" -> categoryTV?.text = CategoriesIds.DEPORTE.category.second
            "13" -> categoryTV?.text = CategoriesIds.LIBROS.category.second
            "14" -> categoryTV?.text = CategoriesIds.NIÑOS_Y_BEBES.category.second
            "15" -> categoryTV?.text = CategoriesIds.AGRICULTURA.category.second
            "16" -> categoryTV?.text = CategoriesIds.SERVICIOS.category.second
            "17" -> categoryTV?.text = CategoriesIds.COLECCIONISMO.category.second
            "18" -> categoryTV?.text = CategoriesIds.CONSTRUCCION.category.second
            "19" -> categoryTV?.text = CategoriesIds.OTROS.category.second

        }

        if (mProduct?.userId != userData?.id) {
            buttonContainer?.visibility = View.GONE
            saveButton?.visibility = View.VISIBLE
        } else {
            buttonContainer?.visibility = View.VISIBLE
            saveButton?.visibility = View.GONE
        }

        //TODO saber que no es mío y que pertenece a un amigo.


//            reserveButton?.visibility = View.GONE
//            reserveButton?.visibility = View.VISIBLE
//        if (mProduct?.reserved == true && mProduct?.userId != userData?.id) {
//            reserveButton?.visibility = View.GONE
//            deleteReserveButton?.visibility = View.VISIBLE
//        } else {
//            deleteReserveButton?.visibility = View.GONE
//        }

        titleTV?.text = mProduct?.name
        descriptionTV?.text = mProduct?.description
        priceTV?.text = "${mProduct?.price}€"
        storeTV?.text = mProduct?.shop

        if (!mProduct?.picture.isNullOrEmpty()) {
            Picasso.get()
                .load(mProduct?.picture)
                .placeholder(R.drawable.ic_placeholder)
                .into(itemImage)
        } else {
            Picasso.get()
                .load(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(itemImage)
        }
        logD("product $mProduct")

        if (!mProduct?.onlineShop.isNullOrEmpty()) {
            goToWebsite?.visibility = View.VISIBLE
        } else {
            goToWebsite?.visibility = View.GONE
        }

        goToWebsite?.setOnClickListener {
            openWeb(mProduct?.onlineShop ?: "http://thegiftcher.com")
        }

        shareButton?.setOnClickListener {
            //TODO abrir intent de compartir
            shareIntent(mProduct?.name.toString())

        }

        deleteReserveButton?.setOnClickListener {

            deleteReservation(mProduct?.id.toString())

        }

        saveButton?.setOnClickListener {
            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                .setTitle("¿Quieres copiar el producto?, el producto aparecerá en tu lista.")
                .setNegativeButton("No", null)
                .setPositiveButton("Si") { _, _ ->
                    copyWishFromUser(mProduct?.userId.toString(), mProduct?.id.toString())
                }.show()
        }

        reserveButton?.setOnClickListener {
            wishToReserve = WishToReserve(mProduct?.userId, mProduct?.id)
            logD("wishToReserve $wishToReserve")
            reserveWish(wishToReserve)

        }

        editWishButton?.setOnClickListener {
            mProduct?.let {
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToEditWishFragment(it)
                Navigation.findNavController(view).navigate(action)
            }
        }

        deleteWishButton?.setOnClickListener {
            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                .setTitle("¿Quieres borrar el producto?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si") { _, _ ->
                    deleteWish(mProduct?.id.toString())
                }.show()
        }
    }

    private fun shareIntent(name: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "¡Quiero $name de regalo!")
            putExtra(Intent.EXTRA_TITLE, "¡Mira mi lista de deseos en The Giftcher!")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun openWeb(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun getFriends() {
        showProgressDialog()
        GlobalScope.launch(Dispatchers.Main) {
            when (val response =
                customRepository.getFriends()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value.friends
                    friends.clear()
                    friends.addAll(responseResult!!.toList())

                    friends.forEach {
                        if (it.friendId == mProduct?.userId && mProduct?.reserved == false) {
                            reserveButton?.visibility = View.VISIBLE
                            logD("regalo de mi amigo y no esta reservado")
                        } else {
                            logD("regalo de un desconocido")
                        }
                    }




                }
                is ResponseResult.Error -> {
                    showError(response.message, requireView())
                }
                is ResponseResult.Forbidden -> {
                    showError(response.message, requireView())

                }
            }

        }
        hideProgressDialog()
    }

    private fun getReservedWishes() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.getReservedWishes()) {
                is ResponseResult.Success -> {
                    val responseResult = response.value.reservedWishes
                    items.clear()

                    responseResult?.forEach { reservedWishes ->
                        reservedWishes.friendReservedWishes?.forEach {
                            items.add(it.id.toString())
                        }
                    }

                    items.forEach {
                        if (mProduct?.id == it){
                            logD("el producto es de un amigo y esta reservado por ti")
                            deleteReserveButton?.visibility = View.VISIBLE
                        } else {
                            logD("el producto no esta reservado por ti")
                        }
                    }
                }
                is ResponseResult.Error -> {
                }
                is ResponseResult.Forbidden -> {
                }
            }
            hideProgressDialog()
        }
    }

    private fun deleteWish(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.deleteWish(id)) {
                is ResponseResult.Success -> {
                    findNavController().popBackStack()
                }
                is ResponseResult.Error -> {
                    logD("Error")
                }
                is ResponseResult.Forbidden -> {
                    logD("Forbidden")
                }
            }
            hideProgressDialog()
        }
    }

    private fun copyWishFromUser(userId: String, wishId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.copyWishFromUser(userId, wishId)) {
                is ResponseResult.Success -> {
                    showMessage("El deseo ha sido copiado correctamente", messageCard)
                    findNavController().popBackStack()
                }
                is ResponseResult.Error -> {
                    logD("Error")
                }
                is ResponseResult.Forbidden -> {
                    logD("Forbidden")
                }
            }
            hideProgressDialog()
        }
    }

    private fun reserveWish(wishId: WishToReserve) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.reserveWish(wishId)) {
                is ResponseResult.Success -> {
                    showMessage("El deseo ha sido reservado correctamente", messageCard)
                    findNavController().popBackStack()
                }
                is ResponseResult.Error -> {
                    logD("Error")
                }
                is ResponseResult.Forbidden -> {
                    logD("Forbidden")
                }
            }
            hideProgressDialog()
        }
    }

    private fun deleteReservation(wishId: String) {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.deleteReservedWish(wishId)) {
                is ResponseResult.Success -> {
                    showMessage("El deseo ha sido reservado correctamente", messageCard)
                    findNavController().popBackStack()
                }
                is ResponseResult.Error -> {
                    logD("Error")
                }
                is ResponseResult.Forbidden -> {
                    logD("Forbidden")
                }
            }
            hideProgressDialog()
        }
    }
}
