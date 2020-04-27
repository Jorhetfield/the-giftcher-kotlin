package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendEditPassword(
    @SerializedName("newPassword") val newPassword: String?,
    @SerializedName("oldPassword") val oldPassword: String?
) : Parcelable, JSONConvertable