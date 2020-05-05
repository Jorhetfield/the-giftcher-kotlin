package com.example.thegiftcherk.features.ui.login.models

import android.os.Parcelable
import android.telephony.cdma.CdmaCellLocation
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WishToReserve(
    @SerializedName("friendId") val friendId: String?,
    @SerializedName("wishId") val wishId: String?
) : Parcelable
