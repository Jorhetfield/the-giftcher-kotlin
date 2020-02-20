package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val token: String,
    val type: String?,
    val id: String?,
    val email: String,
    val name: String,
    @SerializedName("number") var phone: String?,
    var address: String?,
    var nif: String?
)  : Parcelable, JSONConvertable