package com.example.thegiftcherk.features.ui.friends

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Friend(
    val id: String?,
    val name: String?,
    val birthday: String?,
    val picture: String?
) : Parcelable
