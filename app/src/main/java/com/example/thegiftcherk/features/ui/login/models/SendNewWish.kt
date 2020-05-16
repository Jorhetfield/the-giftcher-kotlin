package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendNewWish(
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("shop") val shop: String?,
    @SerializedName("reserved") val reserved: Boolean?,
    @SerializedName("online_shop") val onlineShop: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("category") val category: Int?
) : Parcelable, JSONConvertable
