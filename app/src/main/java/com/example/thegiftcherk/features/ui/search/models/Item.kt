package com.example.thegiftcherk.features.ui.search.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id: String?,
    val name: String?,
    @SerializedName("shop") val shop: String?,
    val price: String?,
    val category: String?,
    val description: String?,
    val picture: String?
) : Parcelable
