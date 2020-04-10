package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendUser(
    @SerializedName("username") val user: String?,
    @SerializedName("password") val password: String
) : Parcelable, JSONConvertable