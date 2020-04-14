package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import com.example.thegiftcherk.setup.utils.extensions.JSONConvertable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String,
    val username:String,
    val lastName:String,
    val mail:String,
    val birthday:String,
    val imageName:String,
    val imagePath:String,
    val token:String
)  : Parcelable, JSONConvertable