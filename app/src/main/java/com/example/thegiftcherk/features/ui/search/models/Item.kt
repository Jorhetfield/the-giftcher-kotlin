package com.example.thegiftcherk.features.ui.search.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id: String?,
    val name: String?,
    val description: String?,
    val picture: String?
) : Parcelable