package com.example.thegiftcherk.features.ui.search.models

import android.os.Parcelable
import android.telephony.cdma.CdmaCellLocation
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id: String?,
    val name: String?,
    @SerializedName("userId") val userId: String?,
    @SerializedName("shop") val shop: String?,
    @SerializedName("onlineShop") val onlineShop: String?,
    val price: String?,
    val reserved: Boolean?,
    val category: String?,
    val description: String?,
    val location: String?,
    @SerializedName("imagePath") val picture: String?
) : Parcelable
