package com.example.thegiftcherk.features.ui.addproduct.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thegiftcherk.R
import com.example.thegiftcherk.setup.utils.extensions.lazyUnsychronized
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail_fragment.*

class ProductDetailFragment : Fragment() {

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

        titleTV?.text = mProduct?.name
        descriptionTV?.text = mProduct?.description
        categoryTV?.text = mProduct?.category
        priceTV?.text = mProduct?.price
        storeTV?.text = mProduct?.store

        Picasso.get()
            .load(mProduct?.picture)
            .into(itemImage)

    }
}
