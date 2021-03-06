package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendEditWish(
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Float?,
    @SerializedName("shop") val shop: String?,
    @SerializedName("imagePath") val image: String?,
    @SerializedName("imageContentType") val imageContentType: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("online_shop") val online_shop: String?,
    @SerializedName("category") val category: Int?
) : Parcelable, JSONConvertable
