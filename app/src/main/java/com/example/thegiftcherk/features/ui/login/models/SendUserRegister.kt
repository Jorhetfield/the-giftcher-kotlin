package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendUserRegister(
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("mail") val mail: String?,
    @SerializedName("password") val password: String,
    @SerializedName("birthday") val birthday: String
) : Parcelable, JSONConvertable