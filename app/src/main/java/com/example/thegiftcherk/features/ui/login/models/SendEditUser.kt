package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendEditUser(
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("birthday") val birthday: String?
) : Parcelable, JSONConvertable