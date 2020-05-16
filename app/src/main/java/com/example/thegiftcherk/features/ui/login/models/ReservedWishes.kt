package com.example.thegiftcherk.features.ui.login.models

import android.content.ClipData
import android.os.Parcelable
import android.telephony.cdma.CdmaCellLocation
import com.example.thegiftcherk.features.ui.search.models.Item
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReservedWishes(
    @SerializedName("friendId") val friendId: String?,
    @SerializedName("friendName") val friendName: String?,
    @SerializedName("friendReservedWishes") val friendReservedWishes: ArrayList<Item>?
) : Parcelable
